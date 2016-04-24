package cn.edu.bnuz.bell.orm

import org.grails.orm.hibernate.cfg.HibernateMappingContextConfiguration
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.IntegerType

/**
 * Created by yanglin on 2016/2/20.
 */
class BellHibernateMappingContextConfiguration extends HibernateMappingContextConfiguration  {
    BellHibernateMappingContextConfiguration() {
        this.addSqlFunction('instr', new StandardSQLFunction('instr', new IntegerType()))
    }
}
