package cn.edu.bnuz.bell.security

/**
 * 用户操作日志
 * @author Yang Lin
 */
class UserLog {
    /**
     * GUID
     */
    String id

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
     * 操作
     */
    String action

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
        id          length: 32, generator: 'guid', comment: '用户日志ID'
        user        comment: '用户'
        ipAddress   length: 50, comment: 'IP地址'
        module      length: 50, comment: '模块'
        item        length: 50, comment: '记录'
        action      length: 250, comment: '操作'
        content     length: 4000, comment: '数据'
        dateCreated comment: '时间'
    }

    static constraints = {
        content     nullable: true, maxSize: 4000
    }
}
