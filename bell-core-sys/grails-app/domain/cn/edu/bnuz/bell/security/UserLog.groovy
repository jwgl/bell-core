package cn.edu.bnuz.bell.security

/**
 * 用户操作日志
 * @author Yang Lin
 */
class UserLog {
    /**
     * ID
     */
    UUID id

    User user
    /**
     * IP地址
     */
    String ipAddress

    /**
     * 模块
     */
    String module

    /**
     * 记录
     */
    String item

    /**
     * 事件
     */
    String event

    /**
     * 内容
     */
    String content

    /**
     * 时间
     */
    Date dateCreated

    static mapping = {
        comment     '用户日志'
        id          generator: 'uuid2', type:'pg-uuid', comment: '用户日志ID'
        user        comment: '用户'
        ipAddress   type: 'text', comment: 'IP地址'
        module      type: 'text', comment: '模块'
        item        type: 'text', comment: '记录'
        event       type: 'text', comment: '事件'
        content     type: 'text', comment: '数据'
        dateCreated comment: '时间'
    }

    static constraints = {
        content     nullable: true, maxSize: 4000
    }
}
