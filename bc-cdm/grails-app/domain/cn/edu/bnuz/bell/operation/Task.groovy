package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.CourseItem


/**
 * 教学任务
 * @author yanglin
 *
 */
class Task {
    /**
     * 教学任务代码
     * <pre>
     * 20131010010010101
     * -----==---===--==
     *   |  |  |  | | |
     *   |  |  |  | | `--- 子任务顺序号(2)
     *   |  |  |  | `----- 任务顺序号(2)
     *   |  |  |  `------- 教学班顺序号(3)
     *   |  |  `---------- 课程顺序号(3)
     *   |  `------------- 部门号(2)
     *   `---------------- 学期(5)
     * </pre>
     */
    Long id

    /**
     * 是否为主任务，主任务关联的学生选课为全部选课学生。
     * <pre>
     * 对于课程项目关系为and的课程的教学任务，有且仅有一个主任务
     * 对于课程项目关系为or的课程的教学任务，所有项目任务为主任务
     * 其它情况，必须设置一个主任务：
     *   理论课 - 理论课为主任务；
     *   理论+实验课 - 理论课为主任务；
     *   实验课 - 实验课为主任务；
     *   实践课 - 实践课为主任务。
     * </pre>
     */
    Boolean isPrimary

    /**
     * 开始周
     */
    Integer startWeek

    /**
     * 结束周
     */
    Integer endWeek

    /**
     * 课程项目。
     */
    CourseItem courseItem

    /**
     * 原系统选课课号
     */
    String originalId

    static belongsTo = [courseClass: CourseClass]

    static hasMany = [
        arrangements: Arrangement,
        students: RegisteredStudent,
        teachers: TaskTeacher
    ]

    static mapping = {
        comment     '教学任务'
        table       schema: 'ea'
        id          generator: 'assigned', comment: '教学任务ID'
        isPrimary   comment: '是否为主任务'
        startWeek   comment: '开始周'
        endWeek     comment: '结束周'
        courseItem  comment: '课程项目'
        originalId  length:31, comment: '原系统选课课号'
        courseClass comment: '所属教学班'
    }

    static constraints = {
        courseItem  nullable: true
        originalId  nullable: true, maxSize: 31
    }
}
