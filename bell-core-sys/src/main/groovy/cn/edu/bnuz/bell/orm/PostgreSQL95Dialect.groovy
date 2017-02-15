package cn.edu.bnuz.bell.orm

import org.hibernate.dialect.PostgreSQL94Dialect
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.StandardBasicTypes

import java.sql.Types

/**
 * PostgreSQL 9.5 Dialect
 * @author Yang Lin
 */
class PostgreSQL95Dialect extends PostgreSQL94Dialect{
    PostgreSQL95Dialect() {
        registerColumnType(Types.JAVA_OBJECT, 'json')
        registerColumnType(PostgreSQLIntegerArrayUserType.SQL_TYPE, 'int[]')
        registerFunction('any_element', new StandardSQLFunction('any', StandardBasicTypes.LONG))
    }

    @Override
    public String getSelectGUIDString() {
        return "select replace(cast(uuid_generate_v4() as text), '-', '')";
    }
}
