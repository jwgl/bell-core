package cn.edu.bnuz.bell.orm

import org.grails.orm.hibernate.cfg.HibernateMappingContextConfiguration
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.IntegerType
import org.hibernate.type.StandardBasicTypes

/**
 * Hibernate映射配置
 * @author Yang Lin
 */
class BellHibernateMappingContextConfiguration extends HibernateMappingContextConfiguration  {
    BellHibernateMappingContextConfiguration() {
        println 'register'
        this.addSqlFunction('instr', new StandardSQLFunction('instr', StandardBasicTypes.INTEGER))
        this.addSqlFunction('generate_series', new StandardSQLFunction('generate_series'))

    }
}
