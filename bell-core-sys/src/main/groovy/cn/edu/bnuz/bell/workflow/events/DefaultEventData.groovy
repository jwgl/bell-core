package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

class DefaultEventData implements EventData {
    String fromUser
    IStateObject entity
    String ipAddress

    UUID getWorkitemId() {
        return null
    }
}
