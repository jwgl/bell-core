package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.StateObject

class AutoEventData implements EventData {
    StateObject entity
    String fromUser
    String comment
    UUID workitemId
    String ipAddress
}
