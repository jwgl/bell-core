package cn.edu.bnuz.bell.orm

import org.hibernate.dialect.PostgreSQL94Dialect

import java.sql.Types

/**
 * PostgreSQL 9.5 Dialect
 * @author Yang Lin
 */
class PostgreSQL95Dialect extends PostgreSQL94Dialect{
    PostgreSQL95Dialect() {
        registerColumnType(Types.JAVA_OBJECT, 'json')
        registerColumnType(PostgreSQLIntegerArrayUserType.SQL_TYPE, 'int[]')

    }

    @Override
    public String getSelectGUIDString() {
        return "select replace(cast(uuid_generate_v4() as text), '-', '')";
    }
}
