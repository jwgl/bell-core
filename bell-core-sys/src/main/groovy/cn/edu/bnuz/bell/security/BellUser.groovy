package cn.edu.bnuz.bell.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User as OssUser

/**
 * 登录用户信息
 * Created by yanglin on 2016/10/5.
 */
class BellUser extends OssUser {
    String id
    String name
    String departmentId
    String longPhone
    String shortPhone
    String email
    UserType userType

    BellUser(User user, Collection<GrantedAuthority> authorities) {
        super(user.id, user.password, user.enabled, !user.accountExpired, !user.passwordExpired,
                !user.accountLocked, authorities)
        this.id = user.id
        this.name = user.name
        this.departmentId = user.departmentId
        this.longPhone = user.longPhone
        this.shortPhone = user.shortPhone
        this.email = user.email
        this.userType = user.userType
    }

    @Override
    public String toString() {
        "id: $id, name: $name, ${super.toString()}"
    }
}