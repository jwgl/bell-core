package cn.edu.bnuz.bell.profile

import cn.edu.bnuz.bell.security.User
import org.codehaus.groovy.util.HashCodeHelper

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
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, user.id)
        hash = HashCodeHelper.updateHash(hash, key)
        hash
    }
}
