package cn.edu.bnuz.bell.security

/**
 * 用户
 * @author Yang Lin
 */
class User {
    /**
     * 用户ID
     */
    String id

    /**
     * 姓名
     */
    String name

    /**
     * 用户ID
     */
    String loginName

    /**
     * 密码
     */
    String password

    /**
     * 是否可用
     */
    Boolean enabled

    /**
     * 帐户是否过期
     */
    Boolean accountExpired

    /**
     * 帐户是否锁定
     */
    Boolean accountLocked

    /**
     * 密码是否过期
     */
    Boolean passwordExpired

    /**
     * 电子邮箱
     */
    String email

    /**
     * 手机
     */
    String longPhone

    /**
     * 短号
     */
    String shortPhone

    /**
     * 用户类型：1-教师;2-学生;9-系统外用户
     */
    UserType userType

    /**
     * 部门ID
     */
    String departmentId

    static hasMany = [
        roles: UserRole
    ]

    static mapping = {
        comment             '用户'
        table               name:'system_user' // USER是DML保留字
        id                  generator: 'assigned', length: 10, comment: '用户ID'
        name                length: 50, comment: '姓名'
        loginName           length: 50, comment: '登录名'
        password            length: 50, comment: '密码'
        enabled             defaultValue: "true", comment: '是否有效'
        accountExpired      defaultValue: "false", comment: '帐户过期'
        accountLocked       defaultValue: "false", comment: '帐户锁定'
        passwordExpired     defaultValue: "false", comment: '密码过期'
        email               length: 50, comment: '邮箱'
        longPhone           length: 11, comment: '手机长号'
        shortPhone          length: 6, comment: '手机短号'
        userType            comment: '用户类型-1:教师;2:学生;9-外部用户'
        departmentId        length: 2, comment: '部门ID'
    }

    static constraints = {
        loginName           nullable: true, unique: true, maxSize: 50
        email               nullable: true, maxSize: 50
        longPhone           nullable: true, maxSize: 11
        shortPhone          nullable: true, maxSize: 6
        departmentId        nullable: true, maxSize: 2
    }

    /**
     * 查找所有具有指定权限的用户
     * @param perm 权限
     * @return 用户列表[id, name]*
     */
    static List<Map<String, Object>> findAllWithPermission(String perm) {
        User.executeQuery '''
select new map(u.id as id, u.name as name)
from User u
join u.roles ur
join ur.role r
join r.permissions rp
where rp.permission.id = :perm
''', [perm: perm]
    }

    /**
     * 查找所有具有指定权限和指定部门的用户
     * @param perm 权限
     * @param departmentId 部门ID
     * @return 用户列表[id, name]*
     */
    static List<Map<String, Object>> findAllWithPermission(String perm, String departmentId) {
        User.executeQuery '''
select new map(u.id as id, u.name as name)
from User u
join u.roles ur
join ur.role r
join r.permissions rp
where rp.permission.id = :perm
and u.departmentId = :departmentId
''', [perm: perm, departmentId: departmentId]
    }
}
