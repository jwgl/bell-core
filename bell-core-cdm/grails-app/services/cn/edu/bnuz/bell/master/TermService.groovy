package cn.edu.bnuz.bell.master

import grails.gorm.transactions.Transactional
import grails.plugin.cache.Cacheable

/**
 * 学期服务
 * @author Yang Lin
 */
@Transactional(readOnly = true)
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