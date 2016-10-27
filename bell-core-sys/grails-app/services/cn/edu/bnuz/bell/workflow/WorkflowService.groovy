package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.User
import grails.transaction.Transactional

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
    def getInstanceWorkitems(UUID workflowInstanceId) {
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

    def getOpenWorkitems(String userId, Long offset, Long max) {
        Workitem.executeQuery '''
select new Map (
  workflow.name as type,
  workitem.id as id,
  instance.title as title,
  workitem.dateCreated as dateCreated,
  workitem.dateReceived as dateReceived,
  fromUser.name as fromUser
)
from Workitem workitem
join workitem.from fromUser
join workitem.instance instance
join instance.workflow workflow
where workitem.to.id = :userId
and workitem.dateProcessed is null
order by workitem.dateCreated desc
''', [userId: userId], [offset: offset, max: max]
    }

    def getClosedWorkitems(String userId, Long offset, Long max) {
        Workitem.executeQuery '''
select new Map (
  workflow.name as type,
  workitem.id as id,
  instance.title as title,
  workitem.dateCreated as dateCreated,
  workitem.dateReceived as dateReceived,
  workitem.dateProcessed as dateProcessed,
  fromUser.name as fromUser
)
from Workitem workitem
join workitem.from fromUser
join workitem.instance instance
join instance.workflow workflow
where workitem.to.id = :userId
and workitem.dateProcessed is not null
order by workitem.dateProcessed desc
''', [userId: userId], [offset: offset, max: max]
    }

    /**
     * 获取未处理和已处理事项数量
     * @param userId 当前用户ID
     * @return [open: openCount, closed: closedCount]
     */
    def getCounts(String userId) {
        def open = Workitem.countByToAndDateProcessedIsNull(User.load(userId))
        def closed = Workitem.countByToAndDateProcessedIsNotNull(User.load(userId))
        return [open: open, closed: closed]
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
     * @param fromNote 备注
     * @return 工作项
     */
    Workitem createWorkItem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Events event,
                            States state,
                            String fromNote) {
        Workitem workItem = new Workitem(
                instance: workflowInstance,
                from: User.load(fromUser),
                event: event,
                state: state,
                note: fromNote,
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
     * @param fromNote 备注
     * @param activity 活动
     * @return 工作项
     */
    Workitem createWorkItem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Events event,
                            States state,
                            String fromNote,
                            String activity) {
        String toUser = getCommitUser(workflowInstance)
        createWorkItem(workflowInstance, fromUser, event, state, fromNote, toUser, activity)
    }

    /**
     * 创建手工工作项，需要目标用户处理
     * @param workflowInstance 工作流实例
     * @param fromUser 源用户
     * @param event 事件
     * @param state 状态
     * @param fromNote 备注
     * @param toUser 目标用户
     * @param activity 活动
     * @return 工作项
     */
    Workitem createWorkItem(WorkflowInstance workflowInstance,
                            String fromUser,
                            Events event,
                            States state,
                            String fromNote,
                            String toUser,
                            String activity) {
        Workitem workItem = new Workitem(
                instance: workflowInstance,
                from: User.load(fromUser),
                event: event,
                state: state,
                note: fromNote,
                to: User.load(toUser),
                activity: WorkflowActivity.load("${workflowInstance.workflow.id}.${activity}"),
                dateCreated: new Date(),
        )
        workItem.save()
    }

    def setProcessed(WorkflowInstance workflowInstance, String toUserId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.dateProcessed = CURRENT_TIMESTAMP
where workitem.instance = :workflowInstance
and workitem.to.id = :toUserId
''', [workflowInstance: workflowInstance, toUserId: toUserId]
    }

    def setProcessed(UUID workItemId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.dateProcessed = CURRENT_TIMESTAMP
where workitem.id = :id
''', [id: workItemId]
    }

    /**
     * 获取提交者ID，如果有多次提交，取最近一位。
     * 主要用于退回和完成通知
     * @param workflowInstance 工作流实例
     * @return 用户ID
     */
    String getCommitUser(WorkflowInstance workflowInstance) {
        List<String> results = Workitem.executeQuery '''
select fromUser.id
from Workitem workitem
join workitem.from fromUser
where workitem.instance = :workflowInstance
and workitem.event = :event
order by workitem.dateCreated desc
''', [workflowInstance: workflowInstance, event: Events.COMMIT], [max: 1]
        results ? results[0] : null
    }
}
