package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Course
import cn.edu.bnuz.bell.organization.Teacher

class TimeplateCourse {
    /**
     * ID
     */
    String id

    /**
     * 操作员
     */
    Teacher operator

    static belongsTo = [course: Course]

    static mapping = {
        comment  '板块课程'
        table    schema: 'ea'
        id       generator: 'assigned', length: 3, comment: '板块课程ID'
        operator comment: '操作员'
        course   comment: '课程'
    }

    static constraints = {
        id       matches: '\\d+'
        course   unique: true
        operator nullable: true
    }
}
