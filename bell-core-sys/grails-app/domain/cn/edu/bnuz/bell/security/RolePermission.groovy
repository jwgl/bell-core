package cn.edu.bnuz.bell.security

import org.apache.commons.lang.builder.HashCodeBuilder

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

        other.role?.id == role?.id &&    other.permission?.id == permission?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (role)
            builder.append(role.id)
        if (permission)
            builder.append(permission.id)
        builder.toHashCode()
    }

    static List<String> findPermissionsByRoles(List<String> roles) {
        RolePermission.executeQuery 'select rp.permission.id from RolePermission rp where rp.role.id in (:roles)', [roles: roles]
    }
}
