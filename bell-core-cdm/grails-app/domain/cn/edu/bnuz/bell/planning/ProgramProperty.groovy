package cn.edu.bnuz.bell.planning

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.master.Property

/**
 * 教学计划-课程性质设置
 * @author Yang Lin
 */
class ProgramProperty implements Serializable  {
    private static final long serialVersionUID = 1

    /**
     * 教学计划
     */
    Program program

    /**
     * 课程性质
     */
    Property property

    /**
     * 学分要求
     */
    Integer credit

    /**
     * 学分上限。
     * 如果为空，不作要求，以学科限制为准；
     * 如果不为空，以设置为准。
     */
    Integer maxCredit

    /**
     * 学分下限
     * 如果为空，不作要求，以学科限制为准；
     * 如果不为空，以设置为准。
     */
    Integer minCredit

    /**
     * 是否计算加权
     */
    Boolean isWeighted

    static belongsTo = [program: Program]

    static mapping = {
        comment    '教学计划-课程性质'
        table      schema: 'ea'
        id         composite: ['program', 'property']
        property   comment: '课程性质'
        credit     comment: '学分要求'
        maxCredit  comment: '学分上限'
        minCredit  comment: '学分下限'
        isWeighted comment: '是否计算加权'
        program    comment: '教学计划'
    }

    static constraints = {
        maxCredit  nullable: true
        minCredit  nullable: true
    }

    boolean equals(other) {
        if (!(other instanceof ProgramProperty)) {
            return false
        }

        other.program?.id == program?.id &&    other.property?.id == property?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (program)
            builder.append(program.id)
        if (property)
            builder.append(property.id)
        builder.toHashCode()
    }
}
