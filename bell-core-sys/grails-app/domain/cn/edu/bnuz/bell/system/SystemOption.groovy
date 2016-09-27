package cn.edu.bnuz.bell.system

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * 选项
 * @author Yang Lin
 */
class SystemOption implements Serializable {
    /**
     * 选项分类
     */
    String type

    /**
     * 选项值
     */
    Integer value

    /**
     * 显示文本
     */
    String text

    /**
     * 显示英文文本
     */
    String englishText

    /**
     * 描述
     */
    String description


    static mapping = {
        comment     '系统选项'
        id          composite: ['type', 'value']
        type        length: 50, comment: '选项分类'
        value       comment: '选项值'
        text        length: 50, comment: '显示文本'
        englishText length: 50, comment: '显示英文文本'
        description length: 250, comment: '说明'
    }

    boolean equals(other) {
        if (!(other instanceof SystemOption)) {
            return false
        }

        other.type == type && other.value == value
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if(type)
            builder.append(type)
        if(value)
            builder.append(value)
        builder.toHashCode()
    }
}
