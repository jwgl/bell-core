package cn.edu.bnuz.bell.planning

import cn.edu.bnuz.bell.master.Period
import cn.edu.bnuz.bell.master.Property
import cn.edu.bnuz.bell.master.Course
import cn.edu.bnuz.bell.organization.Department

/**
 * 教学计划-课程
 * @author Yang Lin
 */
class ProgramCourse {
    /**
     * 学时
     */
    Period period

    /**
     * 是否为必修课
     */
    Boolean isCompulsory

    /**
     * 是否为实践课
     */
    Boolean isPractical

    /**
     * 课程性质
     */
    Property property

    /**
     * 考核方式-1:考试;2-考查;3-论文
     */
    Integer assessType

    /**
     * 考试方式-1:集中;2-分散
     */
    Integer testType

    /**
     * 开始周
     */
    Integer startWeek

    /**
     * 结束周
     */
    Integer endWeek

    /**
     * 建议修读学期
     */
    Integer suggestedTerm

    /**
     * 可开课学期。位标志，1-16位对应正常学期，17-32位对应小学期
     */
    Integer allowedTerm

    /**
     * 课程分组。三位或以上整数，高于两位表示组号，低于两位表示课程群号。
     * 如课程A为101、课程B为101、课程C为102、课程D为102，则表示这四门课程
     * 形成一个课程分组（1=101/100），AB属于组（1）中的群（1=101%100)，CD
     * 属于组（1）中的群（2=102%100)。
     * 课程分组主要用于计划内部课程替换或多课程替换，如外国语专业的专业方向课，
     * 日语（一）、（二）与德语（一）、（二）形成多课替换，可以设置课程分组为：
     * 日语（一）:101
     * 日语（二）:101
     * 德语（一）:102
     * 德语（二）:102
     */
    Integer courseGroup

    /**
     * 排课类型-0:不排课;1:按班级排课;2-按专业排课;3-按模块排课
     */
    Integer scheduleType

    /**
     * 是否多教师同时上课
     */
    Boolean hasMultiTeachers

    /**
     * 专业方向
     */
    Direction direction

    /**
     * 开课学院
     */
    Department department

    /**
     * 课程
     */
    Course course

    static embedded = ['period']

    static belongsTo = [program: Program]

    static mapping = {
        comment          '教学计划-课程'
        table            schema: 'ea'
        id               generator: 'identity', comment: '教学计划-课程ID'
        isCompulsory     defaultValue: "true", comment: '是否必修课'
        isPractical      defaultValue: "false", comment: '是否实践课'
        property         comment: '课程性质'
        assessType       comment: '考核方式'
        testType         comment: '考试方式'
        startWeek        comment: '开始周'
        endWeek          comment: '结束周'
        suggestedTerm    comment: '建议修读学期'
        allowedTerm      comment: '可开课学期'
        courseGroup      defaultValue: "0", comment: '课程分组'
        scheduleType     defaultValue: "1", comment: '排课类别-0:不排课;1:按班级排课;2-按专业排课;3-按模块排课'
        hasMultiTeachers defaultValue: "false", comment: '是否多教师同时上课'
        direction        comment: '专业方向'
        department       comment: '开课学院'
        course           comment: '课程'
        program          comment: '教学计划'
    }

    static constraints = {
        direction        nullable: true
        course           unique: ['program', 'direction']
    }
}
