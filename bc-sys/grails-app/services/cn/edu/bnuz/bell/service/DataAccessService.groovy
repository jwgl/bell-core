package cn.edu.bnuz.bell.service

import cn.edu.bnuz.bell.http.NotFoundException
import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional

/**
 * 数据访问服务
 * @author Yang Lin
 */
@Transactional
@GrailsCompileStatic
class DataAccessService {

    public <D> D find(Class<D> dtoType, String query, Map params) {
        def hql = query.replace('Dto', dtoType.name)

        List results = Dump.executeQuery hql, params
        if (!results) {
            throw new NotFoundException()
        }

        return (D)results[0]
    }

    public Long getLong(String query, Map params) {
        return getValue(Long, query, params);
    }

    public Boolean getBoolean(String query, Map params) {
        return getValue(Boolean, query, params)
    }

    public String getString(String query, Map params) {
        return getValue(String, query, params)
    }

    public Boolean exists(String query, Map params) {
        return getValue(Object, query, params) != null
    }

    private <T> T getValue(Class<T> type, String query, Map params) {
        List results = Dump.executeQuery query, params

        if (!results) {
            return null
        } else {
            return (T)results[0]
        }
    }
}
