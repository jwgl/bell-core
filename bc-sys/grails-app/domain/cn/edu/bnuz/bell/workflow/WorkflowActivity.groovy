package cn.edu.bnuz.bell.workflow

/**
 * 工作流活动
 * Created by yanglin on 2016/3/29.
 */
class WorkflowActivity {
    /**
     * 消息类型ID
     */
    String id

    /**
     * 名称
     */
    String name

    /**
     * URL
     */
    String url

    static belongsTo = [workflow: Workflow]

    static mapping = {
        comment '工作流活动'
        id      length: 50, generator: 'assigned', comment: '活动ID'
        name    length: 50, comment: '名称'
        url     length: 250, comment: 'URL'
    }
}
