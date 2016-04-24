package cn.edu.bnuz.bell.orm

import org.hibernate.dialect.PostgreSQL94Dialect

/**
 * Created by yanglin on 2016/4/19.
 */
class PostgreSQL95Dialect extends PostgreSQL94Dialect{
    @Override
    public String getSelectGUIDString() {
        return "select replace(cast(uuid_generate_v4() as text), '-', '')";
    }
}
