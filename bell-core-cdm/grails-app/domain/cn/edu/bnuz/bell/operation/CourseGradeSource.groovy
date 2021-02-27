package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Term

/**
 * 课程成绩来源
 */
class CourseGradeSource {
    /**
     * 课程成绩来源ID
     */
    UUID id

    /**
     * 学期
     */
    Term term

    /**
     * 选课课号
     */
    String code

    /**
     * 来源类型-1:教学班;2:导入
     */
    Integer type

    static mapping = {
        comment '课程成绩来源'
        table   schema: 'ea'
        id      generator: 'uuid2', type:'pg-uuid', comment: '课程成绩来源ID'
        term    comment: '学期'
        code    comment: '选课课号'
        type    comment: '源类型-1:教学班;2:导入'
    }
}
