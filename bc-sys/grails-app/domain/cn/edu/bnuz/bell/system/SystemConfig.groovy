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
     * 数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间
     */
    char dataType

    /**
     * 描述
     */
    String description

    static mapping = {
        comment     '系统参数'
        id          name: 'key', generator: 'assigned'
        key         length: 50,    comment: '键'
        value       length: 250, comment: '值'
        dataType    comment: '数据类型-I:整数;F:浮点数;B:布尔值;S:字符串;D:日期;T:时间'
        description length: 250, comment: '说明'
    }
}
