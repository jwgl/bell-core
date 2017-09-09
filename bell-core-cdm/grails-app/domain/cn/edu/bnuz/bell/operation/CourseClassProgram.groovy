package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.planning.Program
import org.codehaus.groovy.util.HashCodeHelper

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

        other.courseClass?.id == courseClass?.id && other.program?.id == program?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, courseClass.id)
        hash = HashCodeHelper.updateHash(hash, program.id)
        hash
    }
}
