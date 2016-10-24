package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

class AcceptEventData implements EventData {
    String fromUser
    String toUser
    String comment
    IStateObject entity
    UUID workitemId
    String ipAddress
}
