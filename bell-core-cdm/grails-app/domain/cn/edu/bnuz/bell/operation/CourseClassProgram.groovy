package cn.edu.bnuz.bell.operation

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.planning.Program

/**
 * 教学班-教学计划
 * @Author Yang Lin
 */
class CourseClassProgram implements Serializable {
    CourseClass courseClass
    Program program

    static belongsTo = [courseClass: CourseClass]

    static mapping = {
        comment     '教学班-教学计划'
        table       schema: 'ea'
        id          composite: ['courseClass', 'program']
        courseClass comment: '教学班'
        program     comment: '教学计划'
    }

    boolean equals(other) {
        if (!(other instanceof CourseClassProgram)) {
            return false
        }

        other.courseClass?.id == courseClass?.id &&    other.program?.id == program?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseClass)
            builder.append(courseClass.id)
        if (program)
            builder.append(program.id)
        builder.toHashCode()
    }
}
