package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Event
import cn.edu.bnuz.bell.workflow.State
import cn.edu.bnuz.bell.workflow.WorkflowService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.action.Action

@CompileStatic
abstract class AbstractEntryAction implements Action<State, Event> {
    @Autowired
    protected WorkflowService workflowService
}
