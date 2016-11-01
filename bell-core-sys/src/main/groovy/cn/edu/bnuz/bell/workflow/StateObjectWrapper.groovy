package cn.edu.bnuz.bell.workflow

trait StateObjectWrapper implements StateObject {
    @Override
    Object getId() {
        return this['id']
    }

    @Override
    State getStatus() {
        return this['status'] as State
    }

    @Override
    void setStatus(State state) {
        this['status'] = state
    }

    @Override
    WorkflowInstance getWorkflowInstance() {
        return this['workflowInstance'] as WorkflowInstance
    }

    @Override
    void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this['workflowInstance'] = workflowInstance
    }

    @Override
    String getWorkflowId() {
        return this['workflowId']
    }
}
