package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.SecurityService
import cn.edu.bnuz.bell.security.User

import grails.transaction.Transactional

/**
 * Created by yanglin on 2015/4/23.
 */
@Transactional
class WorkflowService {
    SecurityService securityService

    /**
     * 获取运行实例的工作项
     * @param workflowInstanceId 运行实例ID
     * @return 工作项列表
     */
    def getInstanceWorkitems(String workflowInstanceId) {
        Workitem.executeQuery '''
select new Map (
    fromUser.name as fromUser,
    workitem.action as action,
    toUser.name as toUser,
    workitem.note as note,
    workitem.dateCreated as dateCreated,
    workitem.activity.id as activity
)
from Workitem workitem
join workitem.from fromUser
join workitem.to toUser
where workitem.instance.id = :workflowInstanceId
order by dateCreated desc
''', [workflowInstanceId: workflowInstanceId]
    }

    def getOpenWorkitems(String userId) {
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
and workitem.status = 0
order by workitem.dateCreated desc
''', [userId: userId]
    }

    def getClosedWorkitems(String userId) {
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
and workitem.status = 1
order by workitem.dateCreated desc
''', [userId: userId]
    }

    /**
     * 获取未处理和已处理事项数量
     * @param userId 当前用户ID
     * @return [open: openCount, closed: closedCount]
     */
    def getCounts(String userId) {
        def results = Workitem.executeQuery '''
select new Map(
  a.status as status,
  count(*) as count
)
from Workitem a
where a.to.id = :userId
group by a.status
''', [userId: userId]
        def open = 0;
        def closed = 0;
        results.each {row ->
            switch (row.status) {
                case 0:
                    open += row.count
                    break
                case 1:
                    closed += row.count
                    break
            }
        }
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

    Workitem createWorkItem(WorkflowInstance workflowInstance, String activity, String fromUserId,
                            AuditAction action, String fromNote, String toUserId) {
        if (!workflowInstance) {
            throw new Exception("Workflow instance ${workflowInstance} does not exist.")
        }
        Workitem workItem = new Workitem(
                instance: workflowInstance,
                activity: WorkflowActivity.load("${workflowInstance.workflow.id}.${activity}"),
                from: User.load(fromUserId),
                action: action,
                note: fromNote,
                ip: securityService.ipAddress,
                to: User.load(toUserId),
                dateCreated: new Date(),
                status: 0
        )
        workItem.save()
    }

    def setProcessed(WorkflowInstance workflowInstance, String toUserId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.status = 1,
    workitem.dateProcessed = CURRENT_TIMESTAMP
where workitem.instance = :workflowInstance
and workitem.to.id = :toUserId
and workitem.status = 0
''', [workflowInstance: workflowInstance, toUserId: toUserId]
    }

    def setProcessed(String workItemId) {
        Workitem.executeUpdate '''
update Workitem workitem
set workitem.status = 1,
    workitem.dateProcessed = CURRENT_TIMESTAMP
where workitem.id = :id
and workitem.status = 0
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
and workitem.action = :action
order by workitem.dateCreated desc
''', [workflowInstance: workflowInstance, action: AuditAction.COMMIT], [max: 1]
        results ? results[0] : null
    }
}
