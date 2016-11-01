package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.StateObject

class SubmitEventData implements EventData {
    StateObject entity
    String fromUser
    String comment

    /**
     * 新建时为空
     */
    UUID getWorkitemId() {
        if (!this.entity.workflowInstance) {
            return null
        } else {
            this.entity.workflowInstance.getUnprocessedWorkitemId(fromUser)
        }
    }

    String toUser
    String title
    String ipAddress
}
