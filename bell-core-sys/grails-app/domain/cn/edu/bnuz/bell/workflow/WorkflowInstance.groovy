package cn.edu.bnuz.bell.workflow

import cn.edu.bnuz.bell.security.User

/**
 * 工作流实例
 * @author Yang Lin
 */
class WorkflowInstance {
    /**
     * ID
     */
    UUID id

    /**
     * 工作流
     */
    Workflow workflow

    /**
     * 标题
     */
    String title

    /**
     * 实体ID，用于生成URL
     */
    String entityId

    /**
     * 创建时间
     */
    Date dateCreated

    static hasMany = [
            workitems: Workitem
    ]

    static mapping = {
        comment     '工作流实例'
        id          generator: 'uuid2', type:'pg-uuid', comment: '消息ID'
        workflow    comment: '工作流'
        title       length: 50, comment: '标题'
        entityId    length: 32, comment: '实体ID'
        dateCreated comment: '创建时间'
    }

    UUID getUnprocessedWorkitemId(String userId) {
        def results = Workitem.executeQuery '''
select wi.id
from Workitem wi
where wi.instance = :instance
and wi.to.id = :userId
and wi.dateProcessed is null
''', [instance: this, userId: userId], [max: 1]
        results ? results[0] : null
    }
}
