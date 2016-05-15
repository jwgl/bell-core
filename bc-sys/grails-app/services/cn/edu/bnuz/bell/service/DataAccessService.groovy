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

        List results = Dump.executeQuery hql, params
        if (!results) {
            throw new NotFoundException()
        }

        return (D)results[0]
    }

    public Integer getInteger(String query, Map params = null) {
        return getValue(Integer, query, params)
    }

    public Long getLong(String query, Map params = null) {
        return getValue(Long, query, params);
    }

    public Boolean getBoolean(String query, Map params = null) {
        return getValue(Boolean, query, params)
    }

    public String getString(String query, Map params = null) {
        return getValue(String, query, params)
    }

    public JSONElement getJson(String query, Map params = null) {
        return getValue(JSONElement, query, params)
    }

    public Boolean exists(String query, Map params = null) {
        return getValue(Object, query, params) != null
    }

    private <T> T getValue(Class<T> type, String query, Map params) {
        List results = params ? Dump.executeQuery(query, params) : Dump.executeQuery(query)
        return results ? (T)results[0] : null
    }
}
