package cn.edu.bnuz.bell.system

/**
 * 系统配置数据
 * @author Yang Lin
 */
class SystemConfig {
    /**
     * 键
     */
    String key

    /**
     * 值
     */
    String value

    /**
     * 数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间;M:MAP(JSON)
     */
    char type

    /**
     * 描述
     */
    String description

    static mapping = {
        comment     '系统参数'
        id          name: 'key', generator: 'assigned'
        key         type: 'text', comment: '键'
        value       type: 'text', comment: '值'
        type        comment: '数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间;M:MAP(JSON)'
        description type: 'text', comment: '说明'
    }
}
