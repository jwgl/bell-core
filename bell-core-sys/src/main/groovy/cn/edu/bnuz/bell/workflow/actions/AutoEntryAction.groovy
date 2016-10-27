package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.events.AutoEventData
import cn.edu.bnuz.bell.workflow.events.EventData
import cn.edu.bnuz.bell.workflow.events.ManualEventData
import groovy.transform.CompileStatic
import org.springframework.statemachine.StateContext

@CompileStatic
class AutoEntryAction extends AbstractEntryAction {
    @Override
    void execute(StateContext<States, Events> context) {
        AutoEventData event = context.getMessageHeader(EventData.KEY) as AutoEventData
        workflowService.createWorkItem(event.entity.workflowInstance,
                event.fromUser,
                context.event,
                context.target.id,
                event.comment,
        )
    }
}