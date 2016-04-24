package cn.edu.bnuz.bell.workflow

/**
 * 工作流
 * @author Yang Lin
 */
class Workflow {
    String id
    String name

    static hasMany = [
            activities: WorkflowActivity
    ]

    static mapping = {
        comment '工作流'
        id    	length: 50, generator: 'assigned', comment: '活动ID'
        name    length: 50, comment: '名称'
    }
}
