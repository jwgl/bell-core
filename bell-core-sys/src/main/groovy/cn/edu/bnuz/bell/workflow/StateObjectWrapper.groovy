package cn.edu.bnuz.bell.workflow

trait StateObjectWrapper implements IStateObject {
    @Override
    Object getId() {
        return this['id']
    }

    @Override
    States getStatus() {
        return this['status'] as States
    }

    @Override
    void setStatus(States state) {
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
