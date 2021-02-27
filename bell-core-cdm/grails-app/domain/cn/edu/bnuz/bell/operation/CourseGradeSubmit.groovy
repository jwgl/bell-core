package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Term
import cn.edu.bnuz.bell.organization.Teacher

/**
 * 课程成绩提交
 */
class CourseGradeSubmit {
    /**
     * 课程成绩提交ID
     */
    UUID id

    /**
     * 学期
     */
    Term term

    /**
     * 成绩来源
     */
    CourseGradeSource courseGradeSource

    /**
     * 提交类型：正考、补考、导入、外部、补录
     */
    String submitType

    /**
     * 提交人
     */
    Teacher teacher

    /**
     * 提交时间
     */
    Date dateSubmitted

    static hasMany = [
            assessments: CourseClassAssessment
    ]

    static mapping = {
        comment           '课程成绩提交'
        table             schema: 'ea'
        id                generator: 'uuid2', type:'pg-uuid', comment: '课程成绩提交ID'
        term              comment: '学期'
        courseGradeSource comment: '成绩来源'
        submitType        type: 'text', comment: '提交类型：正考、补考、导入、外部、补录'
        teacher           comment: '提交人'
        dateSubmitted     comment: '提交时间'
    }

    static constraints = {
        teacher       nullable: true
        dateSubmitted nullable: true
    }
}
