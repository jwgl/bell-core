package cn.edu.bnuz.bell.profile

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.security.User

/**
 * 用户设置
 * @author Yang Lin
 */
class UserSetting implements Serializable {
    /**
     * 所属教师
     */
    User user

    /**
     * 键
     */
    String key

    /**
     * 值
     */
    String value

    /**
     * 数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间;M:MAP(JSON);
     */
    char type

    static mapping = {
        comment  '用户设置'
        id       composite: ['user', 'key'], comment: '用户设置ID'
        key      type: 'text', comment: '键'
        value    type: 'text', comment: '值'
        type     comment: '数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间;M:MAP(JSON)'
        user     comment: '所属用户'
    }

    boolean equals(other) {
        if (!(other instanceof UserSetting)) {
            return false
        }

        other.user?.id == user?.id && other.key == key
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if(user)
            builder.append(user.id)
        if(key)
            builder.append(key)
        builder.toHashCode()
    }
}
