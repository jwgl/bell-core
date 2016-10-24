package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.AcceptEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class ApprovedEntryAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<States, Events> context) {
        AcceptEventData event = context.getMessageHeader(EventData.KEY) as AcceptEventData
        workflowService.createWorkItem(
                event.entity.workflowInstance,
                Activities.VIEW,
                context.event,
                event.fromUser,
                event.comment
        )
    }
}