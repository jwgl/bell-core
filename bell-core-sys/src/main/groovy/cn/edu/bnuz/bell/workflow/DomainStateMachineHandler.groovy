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
    private final StateMachine<State, Event> stateMachine
    private final StateMachinePersister<State, Event, StateObject> persister

    @Autowired
    private SecurityService securityService

    public DomainStateMachineHandler(StateMachine<State, Event> stateMachine,
                                     StateMachinePersister<State, Event, StateObject> persister) {
        this.stateMachine = stateMachine
        this.persister = persister
    }

    public State getInitialState() {
        return stateMachine.initialState.id
    }

    public void create(StateObject contextObj, String fromUser) {
        synchronized(stateMachine) {
            stateMachine.stop();
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
            });
            stateMachine.start();
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

    public void update(StateObject entity, String fromUser) {
        this.handleEvent(Event.UPDATE, new AutoEventData(
                entity: entity,
                fromUser: fromUser,
                ipAddress: securityService.ipAddress,
        ))
    }

    public void submit(StateObject entity, String fromUser, String toUser, String comment, String title) {
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
    public void accept(StateObject entity, String fromUser) {
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
    public void accept(StateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Event.ACCEPT, new AutoEventData(
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
    public void accept(StateObject entity, String fromUser, String toUser) {
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
     * @param comment 备注
     * @param toUser 目标用户
     * @param workitemId 来源工作项ID
     */
    public void accept(StateObject entity, String fromUser, String comment, UUID workitemId, String toUser) {
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
    public void reject(StateObject entity, String fromUser) {
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
     * @param comment 备注
     * @param workitemId 来源工作项ID
     */
    public void reject(StateObject entity, String fromUser, String comment, UUID workitemId) {
        this.handleEvent(Event.REJECT, new RejectEventData(
                fromUser: fromUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    public canUpdate(Object entity) {
        return canHandleEvent(Event.UPDATE, entity instanceof StateObject ? entity as StateObject : entity as StateObjectWrapper)
    }

    public canSubmit(StateObject entity) {
        return canHandleEvent(Event.SUBMIT, entity)
    }

    public canAccept(StateObject entity) {
        return canHandleEvent(Event.ACCEPT, entity)
    }

    public canReject(StateObject entity) {
        return canHandleEvent(Event.REJECT, entity)
    }
}
