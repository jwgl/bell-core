package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.ManualEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class ManualEntryAction extends AbstractEntryAction {
    private String activity

    ManualEntryAction(String activity) {
        this.activity = activity
    }

    @Override
    void execute(StateContext<State, Event> context) {
        ManualEventData event = context.getMessageHeader(EventData.KEY) as ManualEventData
        workflowService.createWorkitem(event.entity.workflowInstance,
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