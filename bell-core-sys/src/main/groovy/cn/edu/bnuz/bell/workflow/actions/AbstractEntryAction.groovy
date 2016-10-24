package cn.edu.bnuz.bell.workflow.actions

import cn.edu.bnuz.bell.workflow.Events
import cn.edu.bnuz.bell.workflow.States
import cn.edu.bnuz.bell.workflow.WorkflowService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.action.Action

@CompileStatic
abstract class AbstractEntryAction implements Action<States, Events> {
    @Autowired
    protected WorkflowService workflowService
}
