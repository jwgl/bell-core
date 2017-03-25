package cn.edu.bnuz.bell.service

import cn.edu.bnuz.bell.http.NotFoundException
import grails.compiler.GrailsCompileStatic
import grails.transaction.Transactional
import org.grails.web.json.JSONElement

/**
 * 数据访问服务
 * @author Yang Lin
 */
@Transactional(readOnly = true)
@GrailsCompileStatic
class DataAccessService {

    public <D> D find(Class<D> dtoType, String query, Map params) {
        def hql = query.replace('Dto', dtoType.name)

        List results = Dumb.executeQuery hql, params
        if (!results) {
            throw new NotFoundException()
        }

        return (D)results[0]
    }

    Integer getInteger(String query, Map params = null) {
        return getValue(Integer, query, params)
    }

    Long getLong(String query, Map params = null) {
        return getValue(Long, query, params)
    }

    Boolean getBoolean(String query, Map params = null) {
        return getValue(Boolean, query, params)
    }

    String getString(String query, Map params = null) {
        return getValue(String, query, params)
    }

    UUID getUuid(String query, Map params = null) {
        return getValue(UUID, query, params)
    }

    JSONElement getJson(String query, Map params = null) {
        return getValue(JSONElement, query, params)
    }

    Boolean exists(String query, Map params = null) {
        return getValue(Object, query, params) != null
    }

    private <T> T getValue(Class<T> type, String query, Map params) {
        List results = params ? Dumb.executeQuery(query, params) : Dumb.executeQuery(query)
        return results ? (T)results[0] : null
    }
}
