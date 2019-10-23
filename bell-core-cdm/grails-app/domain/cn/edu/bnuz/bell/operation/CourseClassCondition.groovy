package cn.edu.bnuz.bell.operation

/**
 * 教学班-选课条件
 * @Author Yang Lin
 */
class CourseClassCondition implements Serializable {
    /**
     * 教学班
     */
    CourseClass courseClass

    /**
     * 面向/限制-true:面向;false:限制
     */
    Boolean include

    /**
     * 分组，同一组内的条件为AND关系，并列的组为OR关系
     */
    Integer conditionGroup

    /**
     * 条件名
     */
    String conditionName

    /**
     * 条件值
     */
    String conditionValue

    static belongsTo = [
            courseClass: CourseClass
    ]

    static mapping = {
        comment        '教学班-选课条件'
        table          schema: 'ea'
        id             composite: ['courseClass', 'include', 'conditionGroup', 'conditionName']
        courseClass    comment: '教学班'
        include        comment: '面向/限制-true:面向;false:限制'
        conditionGroup comment: '分组'
        conditionName  type: 'text', comment: '条件名'
        conditionValue type: 'text', comment: '条件值'
    }

    boolean equals(other) {
        if (!(other instanceof CourseClassCondition)) {
            return false
        }

        other.courseClass?.id == courseClass?.id &&
                other.include == include &&
                other.conditionGroup == conditionGroup &&
                other.conditionName == conditionName

    }

    int hashCode() {
        Objects.hash(courseClass.id, include, conditionGroup, conditionName)
    }
}
