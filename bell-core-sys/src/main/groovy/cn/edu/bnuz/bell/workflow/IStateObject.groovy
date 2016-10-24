package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
interface IStateObject {
    States getStatus();
    void setStatus(States state);

    WorkflowInstance getWorkflowInstance();
    void setWorkflowInstance(WorkflowInstance workflowInstance);

    String getWorkflowId();
    Object getId();
}