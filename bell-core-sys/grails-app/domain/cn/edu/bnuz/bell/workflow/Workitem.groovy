package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.User

/**
 * 工作项
 * @author Yang Lin
 */
class Workitem {
    /**
     * ID
     */
    UUID id

    /**
     * 发起人
     */
    User from

    /**
     * 事件
     */
    Events event

    /**
     * 状态
     */
    States state

    /**
     * 发起人备注
     */
    String note

    /**
     * 接收人
     */
    User to

    /**
     * 活动
     */
    WorkflowActivity activity

    /**
     * 工作流实例
     */
    WorkflowInstance instance

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

    static belongsTo = [instance: WorkflowInstance]

    static mapping = {
        comment       '消息'
        id            generator: 'uuid2', type:'pg-uuid', comment: '消息ID'
        from          column: 'from_user', length: 10, comment: '提交用户'
        event         column: 'event', length: 10, comment: '事件'
        state         comment: '状态'
        note          column: 'note', length: 2000, comment: '提交注备'
        to            index:'user_workitem_idx', column: 'to_user', length: 50, comment: '接收用户'
        instance      index: 'instance_workitem_idx', column: 'instance', comment: '实例'
        activity      column: 'activity', length: 50, comment: '活动'
        dateCreated   index: 'instance_workitem_idx', comment: '提交时间'
        dateReceived  comment: '接收时间'
        dateProcessed index:'user_workitem_idx', comment: '办结时间'
    }

    static constraints = {
        note          nullable: true, maxSize: 2000
        to            nullable: true
        activity      nullable: true
        dateReceived  nullable: true
        dateProcessed nullable: true
    }

    String getActivitySuffix() {
        this.activity.id.substring(this.activity.id.lastIndexOf('.') + 1)
    }
}
