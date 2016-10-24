package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.EventData
import cn.edu.bnuz.bell.workflow.events.RejectEventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class RejectedEntryAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<States, Events> context) {
        RejectEventData event = context.getMessageHeader(EventData.KEY) as RejectEventData
        workflowService.createWorkItem(
                event.entity.workflowInstance,
                Activities.VIEW,
                context.event,
                event.fromUser,
                event.comment
        )
    }
}