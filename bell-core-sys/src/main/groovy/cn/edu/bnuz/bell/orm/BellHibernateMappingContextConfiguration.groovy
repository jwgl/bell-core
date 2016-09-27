package cn.edu.bnuz.bell.orm

import org.grails.orm.hibernate.cfg.HibernateMappingContextConfiguration
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.IntegerType

/**
 * Hibernate映射配置
 * @author Yang Lin
 */
class BellHibernateMappingContextConfiguration extends HibernateMappingContextConfiguration  {
    BellHibernateMappingContextConfiguration() {
        this.addSqlFunction('instr', new StandardSQLFunction('instr', new IntegerType()))
    }
}
