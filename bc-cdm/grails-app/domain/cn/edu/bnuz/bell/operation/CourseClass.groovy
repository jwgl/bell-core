package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Period
import cn.edu.bnuz.bell.master.Term
import cn.edu.bnuz.bell.master.Course
import cn.edu.bnuz.bell.master.Property
import cn.edu.bnuz.bell.organization.Department
import cn.edu.bnuz.bell.organization.Teacher

/**
 * 教学班
 * @Author Yang Lin
 */
class CourseClass {
    /**
     * ID
     */
    UUID id

    /**
     * 教学班编号
     */
    String code

    /**
     * 学时
     */
    Period period

    /**
     * 课程性质.
     * 由于存在不同课程性质合班上课的情况，教学班中不记录课程性质，
     * 可以从关联的教学计划中查找相应的课程性质。
     * 当课程班不与计划关联时，记录课程性质，如公选课、板块课、特殊课。
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
     * 是否有效
     */
    Boolean enabled

    /**
     * 开课学期
     */
    Term term

    /**
     * 课程
     */
    Course course

    /**
     * 开课单位
     */
    Department department

    /**
     * 主讲教师
     */
    Teacher teacher

    static embedded = ['period']

    static hasMany = [
        tasks: Task, // 包含任务
        programs: CourseClassProgram // 所属教学计划
    ]

    static mapping = {
        comment    '教学班'
        table      schema: 'ea'
        id         generator: 'uuid2', type:'pg-uuid', comment: '教学班ID'
        code       comment: '教学班编号'
        property   comment: '课程性质'
        assessType comment: '考核方式'
        testType   comment: '考试方式'
        startWeek  comment: '开始周'
        endWeek    comment: '结束周'
        enabled    defaultValue: "true", comment: '是否有效'
        term       comment: '开课学期'
        course     comment: '课程'
        department comment: '开课单位'
        teacher    comment: '主讲教师'
        programs   joinTable: [key: 'course_class_id']
    }

    static constraints = {
        code       maxSize: 31
        teacher    nullable: true
        property   nullable: true
    }
}
