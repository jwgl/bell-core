package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.User
import grails.gorm.transactions.Transactional

/**
 * 工作流服务
 * @author Yang Lin
 */
@Transactional
class WorkflowService {
    /**
     * 获取运行实例的工作项
     * @param workflowInstanceId 运行实例ID
     * @return 工作项列表
     */
    List getInstanceWorkitems(UUID workflowInstanceId) {
        Workitem.executeQuery '''
select new Map (
    fromUser.name as fromUser,
    workitem.event as event,
    workitem.note as note,
    workitem.state as state,
    toUser.name as toUser,
    workitem.dateCreated as dateCreated,
    workitem.dateReceived as dateReceived,
    workitem.dateProcessed as dateProcessed
)
from Workitem workitem
join workitem.from fromUser
left join workitem.to toUser
where workitem.instance.id = :workflowInstanceId
order by dateCreated desc
''', [workflowInstanceId: workflowInstanceId]
    }

    /**
     * 创建工作流实例
     * @param workflowId 工作流ID
     * @param title 标题
     * @param entityId
     * @return
     */
    WorkflowInstance createInstance(String workflowId, String title, Object entityId) {
        WorkflowInstance workflowInstance = new WorkflowInstance(
                workflow: Workflow.load(workflowId),
                title: title,
                entityId: entityId.toString(),
                dateCreated: new Date()
        )
        workflowInstance.save()
    }

    /**
     * 创建自动工作项，不需要用户处理
     * @param workflowInstance 工作流实例
     * @param fromUser 源用户
     * @param event 事件
     * @param state 状态
     * @param note 备注
     * @param ipAddress IP地址
     * @return 工作项
     */
    Workitem createWorkitem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Event event,
                            State state,
                            String note,
                            String ipAddress) {
        Workitem workItem = new Workitem(
                instance: workflowInstance,
                from: User.load(fromUser),
                event: event,
                state: state,
                note: note,
                ipAddress: ipAddress,
                dateCreated: new Date(),
        )
        workItem.save()
    }


    /**
     * 创建手工工作项，需要目标用户处理，目标用户为最后提交者
     * @param workflowInstance 工作流实例
     * @param fromUser 源用户
     * @param event 事件
     * @param state 状态
     * @param note 备注
     * @param ipAddress IP地址
     * @param activity 活动
     * @return 工作项
     */
    Workitem createWorkitem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Event event,
                            State state,
                            String note,
                            String ipAddress,
                            String activity) {
        String toUser = getSubmitUser(workflowInstance)
        createWorkitem(workflowInstance, fromUser, event, state, note, ipAddress, toUser, activity)
    }

    /**
     * 创建手工工作项，需要目标用户处理
     * @param workflowInstance 工作流实例
     * @param fromUser 源用户
     * @param event 事件
     * @param state 状态
     * @param note 备注
     * @param ipAddress IP地址
     * @param toUser 目标用户
     * @param activity 活动
     * @return 工作项
     */
    Workitem createWorkitem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Event event,
                            State state,
                            String note,
                            String ipAddress,
                            String toUser,
                            String activity) {
        Workitem workItem = new Workitem(
                instance: workflowInstance,
                from: User.load(fromUser),
                event: event,
                state: state,
                note: note,
                ipAddress: ipAddress,
                to: User.load(toUser),
                activity: WorkflowActivity.load("${workflowInstance.workflow.id}.${activity}"),
                dateCreated: new Date(),
        )
        workItem.save()
    }

    void setReceived(UUID workitemId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.dateReceived = CURRENT_TIMESTAMP
where workitem.id = :id
''', [id: workitemId]
    }

    void setProcessed(UUID workitemId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.dateProcessed = CURRENT_TIMESTAMP
where workitem.id = :id
''', [id: workitemId]
    }

    /**
     * 获取提交者ID，如果有多次提交，取最近一位。
     * 主要用于退回和完成通知
     * @param workflowInstance 工作流实例
     * @return 用户ID
     */
    String getSubmitUser(WorkflowInstance workflowInstance) {
        List<String> results = Workitem.executeQuery '''
select fromUser.id
from Workitem workitem
join workitem.from fromUser
where workitem.instance = :workflowInstance
and workitem.event = :event
order by workitem.dateCreated desc
''', [workflowInstance: workflowInstance, event: Event.SUBMIT], [max: 1]
        results ? results[0] : null
    }
}
