package cn.edu.bnuz.bell.grails

import groovy.transform.CompileStatic
import org.grails.datastore.mapping.model.MappingContext
import org.springframework.beans.factory.FactoryBean

/**
 * 空MappingContext工厂，防止无数据库访问的应用处理domain class。
 * 在grails-app/conf/spring/resources.groovy中添加grailsDomainClassMappingContext(EmptyMappingContextFactoryBean)
 */
@CompileStatic
class EmptyMappingContextFactoryBean implements FactoryBean<MappingContext> {
    @Override
    MappingContext getObject() throws Exception {
        return null
    }

    @Override
    Class<?> getObjectType() {
        return MappingContext.class
    }

    @Override
    boolean isSingleton() {
        return true
    }
}
