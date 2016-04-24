package cn.edu.bnuz.bell.security

class Role {
    String id
    String name

    static hasMany = [
            permissions: RolePermission
    ]

    static mapping = {
        //cache true
        comment '角色'
        id      length: 100, generator: 'assigned', comment: '角色ID'
        name    length: 50, comment: '名称'
   }
}
