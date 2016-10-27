package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.CommitEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class CommittedEntryAction extends AbstractEntryAction {
    private String activity

    CommittedEntryAction() {
        this(Activities.CHECK)
    }

    CommittedEntryAction(String activity) {
        this.activity = activity
    }

    @Override
    void execute(StateContext<States, Events> context) {
        def event = context.getMessageHeader(EventData.KEY) as CommitEventData
        def workflowInstance = event.entity.workflowInstance

        if (!workflowInstance) {
            workflowInstance = workflowService.createInstance(event.entity.workflowId, event.title, event.entity.id)
            event.entity.workflowInstance = workflowInstance
        }

        workflowService.createWorkItem(
                workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
                event.toUser,
                this.activity,
        )
    }
}
