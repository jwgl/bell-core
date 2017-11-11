package cn.edu.bnuz.bell.operation

/**
 * 排课板块安排。
 */
class TimeplateSchedule {
    /**
     * 排课板块安排
     * <pre>
     * 2016200115010101
     * -----===--==--==
     *   |   |  | | | |
     *   |   |  | | | `------ 安排序号
     *   |   |  | | `-------- 任务序号
     *   |   |  | `---------- 板块序号
     *   |   |  `------------ 年级
     *   |   `--------------- 课程板块号
     *   `------------------- 学期
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
     * 单双周-0:全部;1:单周;2:双周
     */
    Integer oddEven

    /**
     * 星期几-1:星期一;...;7:星期日
     */
    Integer dayOfWeek

    /**
     * 开始节
     */
    Integer startSection

    /**
     * 上课长度
     */
    Integer totalSection

    static belongsTo = [timeplateTask: TimeplateTask]

    static mapping = {
        comment      '排课板块-安排'
        table        schema: 'ea'
        id           generator: 'assigned', comment: '排课板块安排'
        startWeek    comment: '开始周'
        endWeek      comment: '结束周'
        oddEven      comment: '单双周-0:全部;1:单周;2:双周'
        dayOfWeek    comment: '星期几-1:星期一;...;7:星期日'
        startSection comment: '开始节'
        totalSection comment: '上课长度'
    }
}
