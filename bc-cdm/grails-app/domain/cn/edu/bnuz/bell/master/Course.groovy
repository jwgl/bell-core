package cn.edu.bnuz.bell.master

import cn.edu.bnuz.bell.organization.Department

/**
 * 课程
 * @author Yang Lin
 */
class Course {
    /**
     * 课程编号
     */
    String id

    /**
     * 课程名称
     */
    String name

    /**
     * 英文名称
     */
    String englishName

    /**
     * 学分
     */
    BigDecimal credit

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
     * 层次-1:本科;2:硕士;3:博士;9:其他
     */
    Integer educationLevel

    /**
     * 考核方式-1:考试;2:考查;3:论文;9:其他
     */
    Integer assessType

    /**
     * 排课类型-0:不排课;1:按班级排课;2-按专业排课;3-按模块排课。
     * 模版值，实际值见{@link cn.edu.bnuz.bell.planning.ProgramCourse#scheduleType}
     */
    Integer scheduleType

    /**
     * 是否多教师同时上课
     * 模版值，实际值见{@link cn.edu.bnuz.bell.planning.ProgramCourse#hasMultiTeachers}
     */
    Boolean hasMultiTeachers

    /**
     * 课程简介
     */
    String introduction

    /**
     * 是否可用
     */
    Boolean enabled

    /**
     * 所属学院
     */
    Department department

    static embedded = ['period']

    static hasMany = [
        courseItems: CourseItem
    ]

    static mapping = {
        comment          '课程'
        table            schema: 'ea'
        id               generator: 'assigned', length: 8, comment: '课程ID'
        name             length: 100, comment: '名称'
        englishName      length: 200, comment: '英文名称'
        credit           precision: 3, scale: 1, comment: '学分'
        isCompulsory     defaultValue: "true", comment: '是否必修课'
        isPractical      defaultValue: "false", comment: '是否实践课'
        property         comment: '课程性质'
        educationLevel   defaultValue: "1", comment: '层次-1:本科;2:硕士;3:博士;9:其他'
        assessType       defaultValue: "1", comment: '考核方式-1:考试;2:考查;3:论文;9:其他'
        scheduleType     defaultValue: "1", comment: '排课类别-0:不排课;1:按班级排课;2-按专业排课;3-按模块排课'
        hasMultiTeachers defaultValue: "true", comment: '是否多教师同时上课'
        introduction     length: 2000, comment: '课程简介'
        enabled          defaultValue: "true", comment: '是否可用'
        department       comment: '所属学院'
    }

    static constraints = {
        englishName      nullable: true, maxSize: 200
        introduction     nullable: true, maxSize: 2000
    }
}
