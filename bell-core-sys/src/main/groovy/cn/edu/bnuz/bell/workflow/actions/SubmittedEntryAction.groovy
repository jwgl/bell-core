package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.SubmitEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class SubmittedEntryAction extends AbstractEntryAction {
    private String activity

    SubmittedEntryAction(String activity) {
        this.activity = activity
    }

    @Override
    void execute(StateContext<State, Event> context) {
        def event = context.getMessageHeader(EventData.KEY) as SubmitEventData
        def workflowInstance = event.entity.workflowInstance

        if (!workflowInstance) {
            workflowInstance = workflowService.createInstance(event.entity.workflowId, event.title, event.entity.id)
            event.entity.workflowInstance = workflowInstance
        }

        workflowService.createWorkitem(
                workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
                event.ipAddress,
                event.toUser,
                this.activity,
        )
    }
}
