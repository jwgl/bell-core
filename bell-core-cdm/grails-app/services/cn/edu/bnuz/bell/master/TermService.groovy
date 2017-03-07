package cn.edu.bnuz.bell.master

import grails.plugin.cache.Cacheable

/**
 * 学期服务
 * @author Yang Lin
 */
class TermService {
    @Cacheable("term.activeTerm")
    Term getActiveTerm() {
        Term.findByActive(true, [fetch: [swapDates: 'eager']])
    }

    @Cacheable("term.minInSchoolGrade")
    Integer getMinInSchoolGrade() {
        getActiveTerm().id.intdiv(10) - 3
    }
}