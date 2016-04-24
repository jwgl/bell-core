package cn.edu.bnuz.bell.security

/**
 * 权限
 * @author Yang Lin
 */
class Permission {
    String id
    String name

    static mapping = {
        cache   true
        comment '权限'
        id      length: 100, generator: 'assigned', comment: '权限ID'
        name    length: 50, comment: '名称'
    }
}
