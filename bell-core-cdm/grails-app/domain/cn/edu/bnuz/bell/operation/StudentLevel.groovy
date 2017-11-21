package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.organization.Student
import org.codehaus.groovy.util.HashCodeHelper

/**
 * 学生等级
 * @author Yang Lin
 */
class StudentLevel implements Serializable {
    String type
    Integer level

    static belongsTo = [student: Student]

    static mapping = {
        comment '学生-等级'
        table   schema: 'ea'
        id      composite: ['student', 'type'], comment: '学生-等级ID'
        student comment: '学生'
        type    length: 20, comment: '类别'
        level   comment: '等级'
    }

    boolean equals(other) {
        if (!(other instanceof StudentLevel)) {
            return false
        }

        other.student?.id == student?.id && other.type == type
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, student.id)
        hash = HashCodeHelper.updateHash(hash, type)
        hash
    }
}
