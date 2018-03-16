package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.master.CourseItem

/**
 * 排课板块任务。
 */
class TimeplateTask {
    /**
     * 排课板块任务ID
     * <pre>
     * 20162001150101
     * -----===--==--
     *   |   |  | | |
     *   |   |  | | `-- 项目序号
     *   |   |  | `---- 板块序号
     *   |   |  `------ 年级
     *   |   `--------- 课程板块号
     *   `------------- 学期
     * </pre>
     */
    Long id

    /**
     * 开始周
     */
    Integer startWeek

    /**
     * 结束周
     */
    Integer endWeek

    /**
     * 周学时
     */
    Integer period

    /**
     * 课程项目
     */
    CourseItem courseItem

    static belongsTo = [timeplate: Timeplate]

    static mapping = {
        comment       '排课板块-任务'
        table         schema: 'ea'
        id            generator: 'assigned', comment: '排课板块任务ID'
        startWeek     comment: '开始周'
        endWeek       comment: '结束周'
        period        comment: '周学时'
        courseItem    comment: '课程项目'
    }

    static constraints = {
        courseItem  nullable: true
    }
}
