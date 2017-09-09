package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.Course
import cn.edu.bnuz.bell.master.Term

import java.time.Year

/**
 * 排课板块。排课板块(Timeplate)表示一组预定的时间段(TimeplateSlot/TimeplateSchedule)，
 * 用于在不确定选课学生的情况下进行时间冲突判断，判断条件为排课板块对应的行政班(TimeplateAdminClass)。
 * 一个排课板块(Timeplate)包含一到多个板块任务(TimeplateTask)，一个板块任务包含一到多个板块安排(TimeplateTaskSchedule)。
 * 排课过程中，排课板块作为教学班(CourseClass)的模板，板块任务作为教学任务(Task)的模板 ，板块安排作为教学安排(TaskSchedule)的模板。
 * @author Yang Lin
 */
class Timeplate {
    /**
     * 排课板块ID
     * <pre>
     * 20162001201
     * -----===-==
     *   |   | | |
     *   |   | | `-- 板块序号
     *   |   | `---- 年级
     *   |   `------ 课程板块号
     *   `---------- 学期
     * </pre>
     */
    Long id

    /**
     * 学期
     */
    Term term

    /**
     * 课程
     */
    Course course

    /**
     * 年级
     */
    Year grade

    /**
     * 编号
     */
    Integer ordinal

    /**
     * 名称
     */
    String name

    static hasMany = [
            slots       : TimeplateSlot,
            tasks       : TimeplateTask,
            adminClasses: TimeplateAdminClass,
    ]

    static mapping = {
        comment '排课板块'
        table   schema: 'ea'
        id      generator: 'assigned', comment: '排课板块ID'
        term    comment: '学期'
        course  comment: '课程'
        grade   comment: '年级'
        ordinal comment: '序号'
        name    comment: '名称'
    }
}
