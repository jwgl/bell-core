package cn.edu.bnuz.bell.master

import cn.edu.bnuz.bell.calendar.TermSwapDate

import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * 学期
 * @author Yang Lin
 */
class Term {
    /**
     * ID(2012-2013-1 -> 20121)
     */
    Integer id

    /**
     * 开始日期
     */
    LocalDate startDate

    /**
     * 开始周
     */
    Integer startWeek

    /**
     * 结束周（上课结束）
     */
    Integer endWeek

    /**
     * 中期周（长学期结束）
     */
    Integer midLeft

    /**
     * 中期周（小学期开始）
     */
    Integer midRight

    /**
     * 最大周（假期结束）
     */
    Integer maxWeek

    /**
     * 是否为当前学期
     */
    Boolean active

    /**
     * 是否为排课学期
     */
    Boolean schedule


    static hasMany = [
            swapDates: TermSwapDate
    ]

    static mapping = {
        cache     true
        comment   '学期'
        table     schema: 'ea'
        id        generator: 'assigned', comment: '学期ID，2012-2013-1为20121'
        startDate comment: '开始日期'
        startWeek comment: '开始周'
        endWeek   comment: '结束周（上课结束）'
        midLeft   comment: '中期周（长学期结束）'
        midRight  comment: '中期周（小学期开始）'
        maxWeek   comment: '最大周（假期结束）'
        active    defaultValue: "false", comment: '是否为当前学期'
        schedule  defaultValue: "false", comment: '是否为排课学期'
    }

    static constraints = {
        midLeft   nullable: true
        midRight  nullable: true
    }

    /**
     * 根据当前日期计算当前教学周。
     * @return 当前周。如果小于开始周，则取开始周；如果大于结束周，则取结束周。
     */
    def getCurrentWeek() {
        def current =  (int)ChronoUnit.WEEKS.between(startDate, LocalDate.now()) + startWeek
        if(current < startWeek) {
            return startWeek
        } else if(current > endWeek){
            return endWeek
        } else {
            return current
        }
    }

    /**
     * 根据当前日期计算当前工作周。
     * @return 当前周。如果小于开始周，则取开始周；如果大于最大周，则取最大周。
     */
    def getCurrentWorkWeek() {
        def current = (int)ChronoUnit.WEEKS.between(startDate, LocalDate.now()) + startWeek
        if(current < startWeek) {
            return startWeek
        } else if(current > maxWeek){
            return maxWeek
        } else {
            return current
        }
    }
}
