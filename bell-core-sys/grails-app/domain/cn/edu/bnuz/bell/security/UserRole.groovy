package cn.edu.bnuz.bell.security

import org.codehaus.groovy.util.HashCodeHelper

/**
 * 用户-角色
 * @author Yang Lin
 */
class UserRole implements Serializable {
    private static final long serialVersionUID = 1

    User user
    Role role

    static belongsTo = [user: User]

    static constraints = {
        role validator: { Role r, UserRole ur ->
            if (ur.user == null)
                return
            boolean existing = false
            withNewSession {
                existing = exists(ur.user.id, r.id)
            }
            if (existing) {
                return 'userRole.exists'
            }
        }
    }

    static mapping = {
        comment '用户-角色'
        id      composite: ['role', 'user'], comment: '用户角色ID'
        role    comment: '角色'
        user    comment: '用户'
    }

    boolean equals(other) {
        if (!(other instanceof UserRole)) {
            return false
        }

        other.user?.id == user?.id &&    other.role?.id == role?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, user.id)
        hash = HashCodeHelper.updateHash(hash, role.id)
        hash
    }

    static UserRole get(String userId, String roleId) {
        where {
            user == User.load(userId) && role == Role.load(roleId)
        }.get()
    }

    static boolean exists(String userId, String roleId) {
        where {
            user == User.load(userId) && role == Role.load(roleId)
        }.count() > 0
    }

    static UserRole create(User user, Role role, boolean flush = false) {
        def instance = new UserRole(user: user, role: role)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, Role r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = where {
            user == User.load(u.id) && role == Role.load(r.id)
        }.deleteAll()

        if (flush) {
            withSession { it.flush() }
        }

        rowCount > 0
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null)
            return

        where {
            user == User.load(u.id)
        }.deleteAll()

        if (flush) {
            withSession { it.flush() }
        }
    }

    static void removeAll(Role r, boolean flush = false) {
        if (r == null)
            return

        where {
            role == Role.load(r.id)
        }.deleteAll()

        if (flush) {
            withSession { it.flush() }
        }
    }
}
