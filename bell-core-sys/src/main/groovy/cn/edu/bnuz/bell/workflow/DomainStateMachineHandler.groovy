package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.http.BadRequestException
import cn.edu.bnuz.bell.http.ForbiddenException
import cn.edu.bnuz.bell.http.NotFoundException
import cn.edu.bnuz.bell.security.SecurityService
import cn.edu.bnuz.bell.workflow.events.*
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.access.StateMachineAccess
import org.springframework.statemachine.access.StateMachineFunction
import org.springframework.statemachine.persist.StateMachinePersister

@CompileStatic
class DomainStateMachineHandler {
    private final StateMachine<State, Event> stateMachine
    private final StateMachinePersister<State, Event, StateObject> persister
    private final ReviewerProvider reviewerProvider
    @Autowired
    private SecurityService securityService

    DomainStateMachineHandler(StateMachine<State, Event> stateMachine,
                              StateMachinePersister<State, Event, StateObject> persister,
                              ReviewerProvider reviewerProvider) {
        this.stateMachine = stateMachine
        this.persister = persister
        this.reviewerProvider = reviewerProvider
    }

    State getInitialState() {
        return stateMachine.initialState.id
    }

    void create(StateObject contextObj, String fromUser) {
        synchronized(stateMachine) {
            stateMachine.stop()
            stateMachine.getStateMachineAccessor().doWithAllRegions(new StateMachineFunction<StateMachineAccess<State, Event>>() {
                public void apply(StateMachineAccess<State, Event> function) {
                    function.setInitialEnabled(true)
                    function.setForwardedInitialEvent(MessageBuilder
                            .withPayload(Event.CREATE)
                            .setHeader(EventData.KEY, new AutoEventData(
                                fromUser: fromUser,
                                entity: contextObj,
                                ipAddress: securityService.ipAddress,
                             ))
                            .build()
                    )
                }
            })
            stateMachine.start()
        }
    }

    private canHandleEvent(Event event, StateObject contextObj) {
        synchronized (stateMachine) {
            def stateMachine = persister.restore(stateMachine, contextObj)
            def transition = stateMachine.transitions.find {
                it.source == stateMachine.state && it.trigger.event == event
            }
            transition != null
        }
    }

    private handleEvent(Event event, EventData eventData) {
        synchronized(stateMachine) {
            StateObject contextObj = eventData.entity
            def stateMachine = persister.restore(stateMachine, contextObj)

            def message = MessageBuilder
                    .withPayload(event)
                    .setHeader(EventData.KEY, eventData)
                    .build()
            if (stateMachine.sendEvent(message)) {
                persister.persist(stateMachine, contextObj)
            }
        }
    }

    void update(StateObject entity, String fromUser) {
        this.handleEvent(Event.UPDATE, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                ipAddress: securityService.ipAddress,
        ))
    }

    void submit(StateObject entity, String fromUser, String toUser, String comment, String title) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.handleEvent(Event.SUBMIT, new SubmitEventData(
                entity: entity,
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                title: title,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自程序的同意，不产生新的待办事项（自动->自动）
     * @param entity 实体
     * @param fromUser 源用户
     */
    void accept(StateObject entity, String fromUser) {
        this.handleEvent(Event.ACCEPT, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的同意，不产生新的待办事项（人工->自动)
     * @param entity 实体
     * @param fromUser 源用户
     * @param comment 备注
     * @param workitemId 来源工作项ID
     */
    void accept(StateObject entity, String fromUser, String activity,
                String comment, UUID workitemId) {
        this.canAccept(entity, fromUser, activity, workitemId)
        this.handleEvent(Event.ACCEPT, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                comment: comment,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自程序的同意，产生新的待办事项（自动->人工）
     * @param entity 实体
     * @param fromUser 源用户
     */
    void accept(StateObject entity, String fromUser, String toUser) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.handleEvent(Event.ACCEPT, new ManualEventData(
                entity: entity,
                fromUser: fromUser,
                toUser: toUser,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的同意，产生新的待办事项（人工->人工）
     * @param entity 实体
     * @param fromUser 源用户
     * @param activity 活动
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    void accept(StateObject entity, String fromUser, String activity,
                String comment, UUID workitemId, String toUser) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.canAccept(entity, fromUser, activity, workitemId)
        this.handleEvent(Event.ACCEPT, new ManualEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 自来程序的退回，不产生新的待办事项
     * @param entity 实体
     * @param fromUser 源用户
     */
    void reject(StateObject entity, String fromUser) {
        this.handleEvent(Event.REJECT, new AutoEventData(
                fromUser: fromUser,
                entity: entity,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 自来人工的退回，回退发起人
     * @param entity 实体
     * @param fromUser 源用户
     * @param activity 活动
     * @param comment 备注
     * @param workitemId 来源工作项ID
     */
    void reject(StateObject entity, String fromUser, String activity,
                String comment, UUID workitemId) {
        this.canReject(entity, fromUser, activity, workitemId)
        this.handleEvent(Event.REJECT, new RejectEventData(
                fromUser: fromUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的退回，产生新的待办事项（人工->人工）
     * @param entity 实体
     * @param fromUser 源用户
     * @param activity 活动
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    void reject(StateObject entity, String fromUser, String comment,
                UUID workitemId, String toUser) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.handleEvent(Event.REJECT, new ManualEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 自来程序的完成，不产生新的待办事项
     * @param entity 实体
     * @param fromUser 源用户
     */
    void finish(StateObject entity, String fromUser, UUID workitemId) {
        this.handleEvent(Event.FINISH, new AutoEventData(
                fromUser: fromUser,
                entity: entity,
                ipAddress: securityService.ipAddress,
                workitemId: workitemId,
        ))
    }

    /**
     * 回收
     * @param entity 实体
     * @param fromUser 源用户
     */
    void revoke(StateObject entity, String fromUser, String comment) {
        this.handleEvent(Event.REVOKE, new RejectEventData(
                fromUser: fromUser,
                entity: entity,
                comment: comment,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 关闭
     * @param entity 实体
     * @param fromUser 源用户
     * @param workitemId 来源工作项ID
     */
    void close(StateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Event.CLOSE, new RejectEventData(
                fromUser: fromUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的下一步，产生新的待办事项（人工->人工）
     * @param entity 实体
     * @param fromUser 源用户
     * @param activity 活动
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    void next(StateObject entity, String fromUser, String activity,
                String comment, UUID workitemId, String toUser) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.handleEvent(Event.NEXT, new ManualEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的同意（直接同意，fromUser为审核者），产生新的待办事项（人工->人工）
     * @param entity 实体
     * @param fromUser 源用户
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    void approve(StateObject entity, String fromUser,
                String comment, UUID workitemId, String toUser) {
        if (!toUser) {
            throw new BadRequestException('To user is null')
        }

        this.canApprove(entity)
        this.handleEvent(Event.APPROVE, new ManualEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自人工的加签，产生新的待办事项（人工->人工）
     * @param entity 实体
     * @param fromUser 源用户
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    void createReview(StateObject entity, String fromUser,
                String comment, UUID workitemId, String toUser) {
        this.handleEvent(Event.REVIEW, new ManualEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 回滚
     * @param entity 实体
     * @param fromUser 源用户
     * @param workitemId 来源工作项ID
     */
    void rollback(StateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Event.ROLLBACK,new AutoEventData(
                fromUser: fromUser,
                entity: entity,
                ipAddress: securityService.ipAddress,
                workitemId: workitemId,
        ))
    }

    boolean canUpdate(Object entity) {
        return canHandleEvent(Event.UPDATE, entity instanceof StateObject ? entity as StateObject : entity as StateObjectWrapper)
    }

    boolean canSubmit(StateObject entity) {
        return canHandleEvent(Event.SUBMIT, entity)
    }

    boolean canAccept(StateObject entity) {
        return canHandleEvent(Event.ACCEPT, entity)
    }

    boolean canReject(StateObject entity) {
        return canHandleEvent(Event.REJECT, entity)
    }

    boolean canFinish(StateObject entity) {
        return canHandleEvent(Event.FINISH, entity)
    }

    boolean canRevoke(StateObject entity) {
        return canHandleEvent(Event.REVOKE, entity)
    }

    boolean canClose(StateObject entity) {
        return canHandleEvent(Event.CLOSE, entity)
    }

    boolean canApprove(StateObject entity) {
        return canHandleEvent(Event.APPROVE, entity)
    }

    boolean canReview(StateObject entity) {
        return canHandleEvent(Event.REVIEW, entity)
    }

    boolean canRollback(StateObject entity) {
        return canHandleEvent(Event.ROLLBACK, entity)
    }

    /**
     * 是否可同意
     * @param entity 实体
     * @param userId 用户
     * @param activity 活动，如果为空则不检查
     * @param workitemId 工作项
     * @return 是否可同意
     */
    boolean canAccept(StateObject entity, String userId, String activity, UUID workitemId) {
        if (!entity) {
            throw new NotFoundException()
        }

        if (!canAccept(entity)) {
            throw new BadRequestException()
        }

        def workitem = Workitem.get(workitemId)
        if (workitem.dateProcessed || workitem.to.id != userId ) {
            throw new BadRequestException()
        }
        if (activity) {
            if (workitem.activitySuffix != activity) {
                throw new BadRequestException()
            }
        } else {
            activity = workitem.activitySuffix
        }

        checkReviewer(entity.id, userId, activity)
    }

    /**
     * 是否可退回
     * @param entity 实体
     * @param userId 用户
     * @param activity 活动，如果为空则不检查
     * @param workitemId 工作项
     * @return 是否可退回
     */
    def canReject(StateObject entity, String userId, String activity, UUID workitemId) {
        if (!entity) {
            throw new NotFoundException()
        }

        if (!canReject(entity)) {
            throw new BadRequestException()
        }

        def workitem = Workitem.get(workitemId)
        if (workitem.dateProcessed || workitem.to.id != userId ) {
            throw new BadRequestException()
        }
        if (activity) {
            if (workitem.activitySuffix != activity) {
                throw new BadRequestException()
            }
        } else {
            activity = workitem.activitySuffix
        }

        checkReviewer(entity.id, userId, activity)
    }

    void checkReviewer(Object id, String reviewer, String activity) {
        List<Map> reviewers = reviewerProvider.getReviewers(id, activity)
        if (!reviewers.find {it.id == reviewer}) {
            throw new ForbiddenException()
        }
    }
}
