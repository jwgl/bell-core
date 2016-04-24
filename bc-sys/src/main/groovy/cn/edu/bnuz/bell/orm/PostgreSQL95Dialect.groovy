package cn.edu.bnuz.bell.orm

import org.hibernate.dialect.PostgreSQL94Dialect

/**
 * PostgreSQL 9.5 Dialect
 * @author Yang Lin
 */
class PostgreSQL95Dialect extends PostgreSQL94Dialect{
    @Override
    public String getSelectGUIDString() {
        return "select replace(cast(uuid_generate_v4() as text), '-', '')";
    }
}
