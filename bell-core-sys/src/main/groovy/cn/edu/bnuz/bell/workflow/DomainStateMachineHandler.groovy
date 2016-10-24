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

    public void start(IStateObject contextObj) {
        synchronized(stateMachine) {
            this.reset()
            this.persister.persist(stateMachine, contextObj)
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

    private handleEvent(Events event, IStateObject contextObj) {
        synchronized (stateMachine) {
            this.handleEvent(event, new DefaultEventData(
                    fromUser: securityService.userId,
                    entity: contextObj,
                    ipAddress: securityService.ipAddress,
            ))
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

    public void update(IStateObject contextObj) {
        this.handleEvent(Events.UPDATE, contextObj)
    }

    public void commit(String fromUser, String toUser, String title, String comment, IStateObject entity) {
        this.handleEvent(Events.COMMIT, new CommitEventData(
                fromUser: fromUser,
                toUser: toUser,
                title: title,
                comment: comment,
                entity: entity,
                ipAddress: securityService.ipAddress,
        ))
    }

    public void accept(String fromUser, String toUser, String comment, IStateObject entity, UUID workitemId) {
        this.handleEvent(Events.ACCEPT, new AcceptEventData(
                fromUser: fromUser,
                toUser: toUser,
                comment: comment,
                entity: entity,
                workitemId: workitemId,
                ipAddress: securityService.ipAddress,
        ))
    }

    public void reject(String fromUser, String comment, IStateObject entity, UUID workitemId) {
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

    private reset() {
        synchronized(stateMachine) {
            stateMachine.stop();
            stateMachine.getStateMachineAccessor().doWithAllRegions(new StateMachineFunction<StateMachineAccess<States, Events>>() {
                public void apply(StateMachineAccess<States, Events> function) {
                    function.resetStateMachine(null);
                }
            });
            stateMachine.start();
        }
    }
}
