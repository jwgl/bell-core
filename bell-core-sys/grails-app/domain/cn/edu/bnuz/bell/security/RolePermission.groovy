package cn.edu.bnuz.bell.security

import org.codehaus.groovy.util.HashCodeHelper

/**
 * 角色-权限
 * @author Yang Lin
 *
 */
class RolePermission implements Serializable {
    private static final long serialVersionUID = 1

    Role role
    Permission permission

    static belongsTo = [role: Role]

    static mapping = {
        comment    '角色-权限'
        id         composite: ['role', 'permission'], comment: '角色权限ID'
        role       comment: '角色'
        permission comment: '权限'
    }

    boolean equals(other) {
        if (!(other instanceof UserRole)) {
            return false
        }

        other.role?.id == role?.id && other.permission?.id == permission?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, role.id)
        hash = HashCodeHelper.updateHash(hash, permission.id)
        hash
    }

    static List<String> findPermissionsByRoles(List<String> roles) {
        executeQuery 'select distinct rp.permission.id from RolePermission rp where rp.role.id in (:roles)', [roles: roles]
    }
}
