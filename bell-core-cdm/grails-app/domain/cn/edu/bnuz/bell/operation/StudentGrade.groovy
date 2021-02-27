package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Course
import cn.edu.bnuz.bell.master.Term
import cn.edu.bnuz.bell.organization.Student

/**
 * 学生成绩
 */
class StudentGrade implements Serializable {
    /**
     * 课程成绩提交
     */
    CourseGradeSubmit courseGradeSubmit

    /**
     * 学生
     */
    Student student

    /**
     * 学期
     */
    Term term

    /**
     * 课程
     */
    Course course

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
     * 创建时间
     */
    Date dataCreated

    /**
     * 修改时间
     */
    Date dateModified

    /**
     * 备注
     */
    String note

    static mapping = {
        comment           '学生成绩'
        table             schema: 'ea'
        id                composite: ['courseAssessment', 'student']
        courseGradeSubmit comment: '课程考核'
        student           comment: '学生'
        term              comment: '学期'
        course            comment: '课程'
        letterGrade       type: 'text', comment: '字符成绩'
        percentageGrade   precision: 5, scale: 2, comment: '百分制成绩'
        assessFlag        type: 'text', comment: '考核标记'
        note              type: 'text', comment: '备注'
        dataCreated       comment: '创建时间'
        dateModified      comment: '修改时间'
    }

    boolean equals(other) {
        if (!(other instanceof StudentGrade)) {
            return false
        }

        other.courseGradeSubmit?.id == courseGradeSubmit?.id && other.student.id == student.id
    }

    int hashCode() {
        Objects.hash(courseGradeSubmit.id, student.id)
    }
}
