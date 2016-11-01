package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

/**
 * 通知发起人
 */
@CompileStatic
class NotifySubmitterAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<State, Event> context) {
        EventData event = context.getMessageHeader(EventData.KEY) as EventData
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