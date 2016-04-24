package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.User

/**
 * 工作项
 * @author Yang Lin
 */
class Workitem {
    String id

    /**
     * 发起人
     */
    User from

    /**
     * 发起人操作
     */
    AuditAction action

    /**
     * 发起人备注
     */
    String note

    /**
     * 发起人IP
     */
    String ip

    /**
     * 接收人
     */
    User to

    /**
     * 工作流实例
     */
    WorkflowInstance instance

    /**
     * 活动
     */
    WorkflowActivity activity

    /**
     * 提交时间
     */
    Date dateCreated

    /**
     * 接收时间
     */
    Date dateReceived

    /**
     * 办结时间
     */
    Date dateProcessed

    /**
     * 状态：0-未处理 1-已处理
     */
    Integer status

    static belongsTo = [instance: WorkflowInstance]

    static mapping = {
        comment       '消息'
        id            length: 32, generator: 'guid', comment: '消息ID'
        from          column: 'from_user', length: 10, comment: '提交用户'
        action        column: 'from_action', comment: '提交操作'
        note          column: 'from_note', length: 2000, comment: '提交注备'
        ip            column: 'from_ip', length:50, comment: '提交IP'
        to            index:'user_workitem_idx', column: 'to_user', length: 50, comment: '接收用户'
        instance      index: 'instance_workitem_idx', column: 'instance', comment: '实例'
        activity      column: 'activity', length: 50, comment: '活动'
        dateCreated   index:'user_workitem_idx,instance_workitem_idx', comment: '提交时间'
        dateReceived  comment: '接收时间'
        dateProcessed comment: '办结时间'
        status        index:'user_workitem_idx', comment: '状态：0-未处理 1-已处理'
    }

    static constraints = {
        note          nullable: true, maxSize: 2000
        dateReceived  nullable: true
        dateProcessed nullable: true
    }

    String getActivitySuffix() {
        return this.activity.id.substring(this.activity.id.lastIndexOf('.') + 1)
    }
}
