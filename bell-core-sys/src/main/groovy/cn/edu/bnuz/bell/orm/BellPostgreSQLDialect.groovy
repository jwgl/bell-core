package cn.edu.bnuz.bell.orm

import org.hibernate.dialect.PostgreSQL94Dialect
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.StandardBasicTypes

class BellPostgreSQLDialect extends PostgreSQL94Dialect {
    BellPostgreSQLDialect() {
        registerColumnType(PostgreSQLIntegerArrayUserType.SQL_TYPE, 'int[]')

        this.registerFunction('instr', new StandardSQLFunction('instr', StandardBasicTypes.INTEGER))
        this.registerFunction('generate_series', new StandardSQLFunction('generate_series'))
        this.registerFunction('any_element', new StandardSQLFunction('any', StandardBasicTypes.LONG))
    }
}
