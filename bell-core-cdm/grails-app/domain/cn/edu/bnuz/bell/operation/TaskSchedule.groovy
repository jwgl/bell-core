package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.organization.Teacher
import cn.edu.bnuz.bell.place.Place

/**
 * 教学安排
 * @author Yang Lin
 */
class TaskSchedule {
    UUID id
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

    /**
     * 场地
     */
    Place place

    /**
     * 教师
     */
    Teacher teacher

    /**
     * 调课引用的原始排课安排
     */
    TaskSchedule root

    /**
     * 周次位标记
     */
    Integer weekBits

    /**
     * 节次位标记
     */
    Integer sectionBits

    static belongsTo = [task: Task]

    static mapping = {
        comment      '教学安排'
        table        schema: 'ea'
        id           generator: 'uuid2', type:'pg-uuid', comment: '教学安排ID'
        endWeek      comment: '结束周'
        oddEven      comment: '单双周-0:全部;1:单周;2:双周'
        startWeek    comment: '开始周'
        dayOfWeek    comment: '星期几-1:星期一;...;7:星期日'
        startSection comment: '开始节'
        totalSection comment: '上课长度'
        place        comment: '教学场地'
        teacher      index: 'task_schedule_teacher_idx', comment: '教师'
        task         comment: '所属教学任务'
        root         comment: '调课引用的原始排课安排'
        weekBits     comment: '周次位标记'
        sectionBits  comment: '节次位标记'
    }

    static constraints = {
        place        nullable: true
        root         nullable: true
        weekBits     nullable: true
        sectionBits  nullable: true
    }
}
