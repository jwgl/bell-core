package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

class ManualEventData implements EventData {
    IStateObject entity
    String fromUser
    String comment
    UUID workitemId
    String toUser
    String activity
    String ipAddress
}
