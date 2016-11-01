package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
interface StateObject {
    State getStatus();
    void setStatus(State state);

    WorkflowInstance getWorkflowInstance();
    void setWorkflowInstance(WorkflowInstance workflowInstance);

    String getWorkflowId();
    Object getId();
}