package cn.edu.bnuz.bell.operation

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.organization.Student

/**
 * 选课学生
 * @author yanglin
 *
 */
class RegisteredStudent implements Serializable {
    private static final long serialVersionUID = 1

    /**
     * 教学任务
     */
    Task teachingTask

    /**
     * 学生
     */
    Student student

    /**
     * 选课时间
     */
    Date dateCreated

    /**
     * 选课方式-0:排课;1:选课;9:特殊处理
     */
    Integer registerType

    static mapping = {
        comment      '学生选课'
        table        schema: 'ea'
        id           composite: ['teachingTask', 'student'], comment: '选课ID'
        teachingTask comment: '教学任务'
        student      comment: '学生'
        dateCreated  comment: '选课时间'
        registerType comment: '选课方式-0:排课;1:选课;9:特殊处理'
    }

    boolean equals(other) {
        if (!(other instanceof RegisteredStudent)) {
            return false
        }

        other.teachingTask?.id == teachingTask?.id &&    other.student?.id == student?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (teachingTask)
            builder.append(teachingTask.id)
        if (student)
            builder.append(student.id)
        builder.toHashCode()
    }
}
