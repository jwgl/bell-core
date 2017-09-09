package cn.edu.bnuz.bell.operation

import org.codehaus.groovy.util.HashCodeHelper

/**
 * 排课板块时段。板块的初步时间安排，将在板块安排中细化。
 */
class TimeplateSlot implements Serializable {
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

    static belongsTo = [timeplate: Timeplate]

    static mapping = {
        comment      '排课板块时段'
        table        schema: 'ea'
        id           composite: ['timeplate', 'oddEven', 'dayOfWeek', 'startSection', 'totalSection'], comment: '排课板块时段ID'
        oddEven      comment: '单双周-0:全部;1:单周;2:双周'
        dayOfWeek    comment: '星期几-1:星期一;...;7:星期日'
        startSection comment: '开始节'
        totalSection comment: '上课长度'
    }

    boolean equals(other) {
        if (!(other instanceof TimeplateSlot)) {
            return false
        }

        other.timeplate?.id == timeplate?.id &&
                other.oddEven == oddEven &&
                other.dayOfWeek == dayOfWeek &&
                other.startSection == startSection &&
                other.totalSection == totalSection
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, timeplate.id)
        hash = HashCodeHelper.updateHash(hash, oddEven)
        hash = HashCodeHelper.updateHash(hash, dayOfWeek)
        hash = HashCodeHelper.updateHash(hash, startSection)
        hash = HashCodeHelper.updateHash(hash, totalSection)
        hash
    }
}
