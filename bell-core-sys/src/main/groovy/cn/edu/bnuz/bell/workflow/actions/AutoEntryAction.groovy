package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.events.AutoEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class AutoEntryAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<State, Event> context) {
        AutoEventData event = context.getMessageHeader(EventData.KEY) as AutoEventData
        workflowService.createWorkitem(event.entity.workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
        )
    }
}