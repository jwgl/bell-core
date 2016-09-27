package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.CourseItem

/**
 * 教学任务
 * @author Yang Lin
 */
class Task {
    /**
     * ID
     */
    UUID id

    /**
     * 教学任务编号
     */
    String code

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

    static belongsTo = [courseClass: CourseClass]

    static hasMany = [
        schedules: TaskSchedule,
        students: TaskStudent,
        teachers: TaskTeacher
    ]

    static mapping = {
        comment     '教学任务'
        table       schema: 'ea'
        id          generator: 'uuid2', type:'pg-uuid', comment: '教学任务ID'
        code        comment: '教学任务编号'
        isPrimary   comment: '是否为主任务'
        startWeek   comment: '开始周'
        endWeek     comment: '结束周'
        courseItem  comment: '课程项目'
        courseClass comment: '所属教学班'
    }

    static constraints = {
        code        maxSize: 31
        courseItem  nullable: true
    }
}
