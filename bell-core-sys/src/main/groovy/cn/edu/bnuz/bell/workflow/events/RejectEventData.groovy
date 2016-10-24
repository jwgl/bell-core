package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

class RejectEventData implements EventData {
    String fromUser
    String comment
    IStateObject entity
    UUID workitemId
    String ipAddress
}
