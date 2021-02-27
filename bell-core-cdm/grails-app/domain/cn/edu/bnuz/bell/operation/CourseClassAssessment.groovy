package cn.edu.bnuz.bell.operation

/**
 * 教学班考核方案
 */
class CourseClassAssessment implements Serializable {
    /**
     * 考核阶段：平时、期中、期末、实验
     */
    String assessStage

    /**
     * 考核方式：考试、考查、毕业论文
     */
    String assessType

    /**
     * 成绩占比
     */
    BigDecimal assessRatio

    static belongsTo = [
            courseClass: CourseClass
    ]

    static mapping = {
        comment     '教学班考核方案'
        table       schema: 'ea'
        id          composite: ['courseClass', 'assessStage']
        courseClass comment: '教学班'
        assessStage type: 'text', comment: '考核阶段'
        assessType  type: 'text', comment: '考核方式'
        assessRatio precision: 3, scale: 2, comment: '成绩占比'
    }

    boolean equals(other) {
        if (!(other instanceof CourseClassAssessment)) {
            return false
        }

        other.courseClass?.id == courseClass?.id && other.assessStage == assessStage
    }

    int hashCode() {
        Objects.hash(courseClass.id, assessStage)
    }
}
