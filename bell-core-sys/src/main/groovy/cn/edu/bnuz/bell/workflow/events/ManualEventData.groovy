package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.StateObject

class ManualEventData implements EventData {
    StateObject entity
    String fromUser
    String comment
    UUID workitemId
    String toUser
    String activity
    String ipAddress
}
