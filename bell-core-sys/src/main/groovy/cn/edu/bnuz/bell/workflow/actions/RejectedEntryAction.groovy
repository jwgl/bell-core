package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.EventData
import cn.edu.bnuz.bell.workflow.events.RejectEventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class RejectedEntryAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<State, Event> context) {
        RejectEventData event = context.getMessageHeader(EventData.KEY) as RejectEventData
        workflowService.createWorkitem(
                event.entity.workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
                event.ipAddress,
                Activities.VIEW,
        )
    }
}