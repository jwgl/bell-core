package cn.edu.bnuz.bell.security

/**
 * 角色
 * @author Yang Lin
 */
class Role {
    String id
    String name

    static hasMany = [
            permissions: RolePermission
    ]

    static mapping = {
        comment '角色'
        id      length: 100, generator: 'assigned', comment: '角色ID'
        name    length: 50, comment: '名称'
   }
}
