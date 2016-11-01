package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.WorkflowService
import cn.edu.bnuz.bell.workflow.events.EventData
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateContext
import org.springframework.statemachine.action.Action

@CompileStatic
class WorkitemProcessedAction implements Action<State, Event> {
    @Autowired
    protected WorkflowService workflowService

    @Override
    void execute(StateContext<State, Event> context) {
        EventData event = context.getMessageHeader(EventData.KEY) as EventData
        UUID workitemId = event.workitemId
        if (workitemId) {
            workflowService.setProcessed(workitemId)
        }
    }
}
