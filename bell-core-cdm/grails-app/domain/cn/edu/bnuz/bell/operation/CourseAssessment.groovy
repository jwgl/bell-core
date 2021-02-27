package cn.edu.bnuz.bell.operation

/**
 * 课程考核
 */
class CourseAssessment {
    /**
     * 课程考核ID
     */
    UUID id

    /**
     * 成绩来源
     */
    CourseGradeSource courseGradeSource

    /**
     * 课程成绩提交
     */
    CourseGradeSubmit courseGradeSubmit

    /**
     * 考核阶段：平时、期中、期末、实验、总评、缓考、补考
     */
    String assessStage

    static belongsTo = [courseClass: CourseClass]

    static hasMany = [
            courseAssessmentGrades: CourseAssessmentGrade
    ]

    static mapping = {
        comment           '课程考核'
        table             schema: 'ea'
        id                generator: 'uuid2', type:'pg-uuid', comment: '课程考核ID'
        courseGradeSource comment: '课程成绩来源'
        courseGradeSubmit comment: '课程成绩提交'
        assessStage       type: 'text', comment: '考核阶段'
    }

    static constraints = {
        courseGradeSubmit nullable: true
    }
}
