package cn.edu.bnuz.bell.security

import cn.edu.bnuz.bell.system.Menu
import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * 用户-角色
 * @author yanglin
 *
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
            UserRole.withNewSession {
                existing = UserRole.exists(ur.user.id, r.id)
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
        def builder = new HashCodeBuilder()
        if (user)
            builder.append(user.id)
        if (role)
            builder.append(role.id)
        builder.toHashCode()
    }

    static UserRole get(String userId, String roleId) {
        UserRole.where {
            user == User.load(userId) && role == Role.load(roleId)
        }.get()
    }

    static boolean exists(String userId, String roleId) {
        UserRole.where {
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

        int rowCount = UserRole.where {
            user == User.load(u.id) && role == Role.load(r.id)
        }.deleteAll()

        if (flush) {
            UserRole.withSession { it.flush() }
        }

        rowCount > 0
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null)
            return

        UserRole.where {
            user == User.load(u.id)
        }.deleteAll()

        if (flush) {
            UserRole.withSession { it.flush() }
        }
    }

    static void removeAll(Role r, boolean flush = false) {
        if (r == null)
            return

        UserRole.where {
            role == Role.load(r.id)
        }.deleteAll()

        if (flush) {
            UserRole.withSession { it.flush() }
        }
    }
}
