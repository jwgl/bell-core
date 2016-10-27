package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Activities
import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

/**
 * 通知发起人
 */
@CompileStatic
class NotifyCommitterAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<States, Events> context) {
        EventData event = context.getMessageHeader(EventData.KEY) as EventData
        workflowService.createWorkItem(
                event.entity.workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
                Activities.VIEW,
        )
    }
}