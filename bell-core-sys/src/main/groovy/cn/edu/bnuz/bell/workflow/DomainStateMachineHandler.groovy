package cn.edu.bnuz.bell.workflow

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
    private final StateMachine<States, Events> stateMachine
    private final StateMachinePersister<States, Events, IStateObject> persister

    @Autowired
    private SecurityService securityService

    public DomainStateMachineHandler(StateMachine<States, Events> stateMachine,
                                     StateMachinePersister<States, Events, IStateObject> persister) {
        this.stateMachine = stateMachine
        this.persister = persister
    }

    public States getInitialState() {
        return stateMachine.initialState.id
    }

    public void create(IStateObject contextObj, String fromUser) {
        synchronized(stateMachine) {
            stateMachine.stop();
            stateMachine.getStateMachineAccessor().doWithAllRegions(new StateMachineFunction<StateMachineAccess<States, Events>>() {
                public void apply(StateMachineAccess<States, Events> function) {
                    function.setInitialEnabled(true)
                    function.setForwardedInitialEvent(MessageBuilder
                            .withPayload(Events.CREATE)
                            .setHeader(EventData.KEY, new AutoEventData(
                                fromUser: fromUser,
                                entity: contextObj,
                                ipAddress: securityService.ipAddress,
                             ))
                            .build()
                    )
                }
            });
            stateMachine.start();
        }
    }

    private canHandleEvent(Events event, IStateObject contextObj) {
        synchronized (stateMachine) {
            def stateMachine = persister.restore(stateMachine, contextObj)
            def transition = stateMachine.transitions.find {
                it.source == stateMachine.state && it.trigger.event == event
            }
            transition != null
        }
    }

    private handleEvent(Events event, EventData eventData) {
        synchronized(stateMachine) {
            IStateObject contextObj = eventData.entity
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

    public void update(IStateObject entity, String fromUser) {
        this.handleEvent(Events.UPDATE, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                ipAddress: securityService.ipAddress,
        ))
    }

    public void commit(IStateObject entity, String fromUser, String toUser, String comment, String title) {
        this.handleEvent(Events.COMMIT, new CommitEventData(
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
    public void accept(IStateObject entity, String fromUser) {
        this.handleEvent(Events.ACCEPT, new AutoEventData(
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
    public void accept(IStateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Events.ACCEPT, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                comment: comment,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    /**
     * 来自程序的同意，不产生新的待办事项（自动->人工）
     * @param entity 实体
     * @param fromUser 源用户
     */
    public void accept(IStateObject entity, String fromUser, String toUser) {
        this.handleEvent(Events.ACCEPT, new ManualEventData(
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
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    public void accept(IStateObject entity, String fromUser, String comment, UUID workitemId, String toUser) {
        this.handleEvent(Events.ACCEPT, new ManualEventData(
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
    public void reject(IStateObject entity, String fromUser) {
        this.handleEvent(Events.REJECT, new RejectEventData(
                fromUser: fromUser,
                entity: entity,
                ipAddress: securityService.ipAddress,
        ))
    }


    /**
     * 自来人工的退回，回退发起人
     * @param entity 实体
     * @param fromUser 源用户
     * @param comment 备注
     * @param workitemId 来源工作项ID
     */
    public void reject(IStateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Events.REJECT, new RejectEventData(
                fromUser: fromUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    public canUpdate(Object entity) {
        return canHandleEvent(Events.UPDATE, entity instanceof IStateObject ? entity as IStateObject : entity as StateObjectWrapper)
    }

    public canCommit(IStateObject entity) {
        return canHandleEvent(Events.COMMIT, entity)
    }

    public canAccept(IStateObject entity) {
        return canHandleEvent(Events.ACCEPT, entity)
    }

    public canReject(IStateObject entity) {
        return canHandleEvent(Events.REJECT, entity)
    }
}
