package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.WorkflowService
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action

@CompileStatic
class WorkitemProcessedAction implements Action<States, Events> {
    @Autowired
    protected WorkflowService workflowService

    @Override
    void execute(StateContext<States, Events> context) {
        EventData event = context.getMessageHeader(EventData.KEY) as EventData
        UUID workitemId = event.workitemId
        if (workitemId) {
            workflowService.setProcessed(workitemId)
        }
    }
}
