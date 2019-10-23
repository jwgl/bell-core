package cn.edu.bnuz.bell.calendar

import cn.edu.bnuz.bell.master.Term

import java.time.LocalDate

/**
 * 校历调换日期
 * @author Yang Lin
 *
 */
class TermSwapDate {
    /**
     * 源日期
     */
    LocalDate fromDate

    /**
     * 目标日期
     */
    LocalDate toDate

    static belongsTo = [term: Term]

    static mapping = {
        comment  '校历调换日期'
        table    schema: 'ea'
        fromDate comment: '源日期'
        toDate   comment: '目标日期'
        term     comment: '学期'
    }
}
