package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.organization.Student

/**
 * 课程考核成绩
 */
class CourseAssessmentGrade implements Serializable {
    /**
     * 学生
     */
    Student student

    /**
     * 字符成绩
     */
    String letterGrade

    /**
     * 百分制成绩
     */
    BigDecimal percentageGrade

    /**
     * 考核标记
     */
    String assessFlag

    /**
     * 备注
     */
    String note

    static belongsTo = [courseAssessment: CourseAssessment]

    static mapping = {
        comment          '课程考核成绩'
        table            schema: 'ea'
        id               composite: ['courseAssessment', 'student']
        courseAssessment comment: '课程考核'
        student          comment: '学生'
        letterGrade      type: 'text', comment: '字符成绩'
        percentageGrade  precision: 5, scale: 2, comment: '百分制成绩'
        assessFlag       type: 'text', comment: '考核标记'
        note             type: 'text', comment: '备注'
    }

    static constraints = {
        letterGrade nullable: true
    }

    boolean equals(other) {
        if (!(other instanceof CourseAssessmentGrade)) {
            return false
        }

        other.courseAssessment?.id == courseAssessment?.id && other.student.id == student.id
    }

    int hashCode() {
        Objects.hash(courseAssessment.id, student.id)
    }
}
