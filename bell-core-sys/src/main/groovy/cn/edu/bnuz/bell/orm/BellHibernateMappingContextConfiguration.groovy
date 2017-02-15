package cn.edu.bnuz.bell.orm

import org.grails.orm.hibernate.cfg.HibernateMappingContextConfiguration
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.StandardBasicTypes

/**
 * Hibernate映射配置
 * @author Yang Lin
 */
class BellHibernateMappingContextConfiguration extends HibernateMappingContextConfiguration  {
    BellHibernateMappingContextConfiguration() {
        this.addSqlFunction('instr', new StandardSQLFunction('instr', StandardBasicTypes.INTEGER))
        this.addSqlFunction('generate_series', new StandardSQLFunction('generate_series'))
        this.addSqlFunction('any_element', new StandardSQLFunction('any', StandardBasicTypes.LONG))

        this.registerTypeContributor { typeContributions, serviceRegistry ->
            typeContributions.contributeType(PostgreSQLIntegerArrayUserType.INSTANCE, 'int[]')
        }
    }
}
