package cn.edu.bnuz.bell.orm

import grails.converters.JSON
import org.grails.web.json.JSONElement
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.usertype.UserType
import org.postgresql.util.PGobject

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

/**
 * Hibernate postgreSQL json 用户类型
 * @author Yang Lin
 */
class PostgreSQLJsonUserType implements UserType {
    @Override
    int[] sqlTypes() {
        [Types.JAVA_OBJECT] as int[]
    }

    @Override
    Class returnedClass() {
        JSONElement
    }

    @Override
    boolean equals(Object o, Object o2) throws HibernateException {
        Objects.equals(o, o2)
    }

    @Override
    int hashCode(Object o) throws HibernateException {
        o.hashCode()
    }

    @Override
    Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (resultSet.getObject(names[0]) == null) {
            return null
        } else {
            PGobject object = (PGobject) resultSet.getObject(names[0])
            return JSON.parse(object.value)
        }
    }

    @Override
    void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.NULL)
        } else {
            JSON json = value as JSON
            String jsonString = json.toString()
            PGobject pGobject = new PGobject()
            pGobject.setType("json")
            pGobject.setValue(jsonString)
            preparedStatement.setObject(index, pGobject)
        }
    }

    @Override
    Object deepCopy(Object o) throws HibernateException {
        return o
    }

    @Override
    boolean isMutable() {
        return false
    }

    @Override
    Serializable disassemble(Object o) throws HibernateException {
        (Serializable) this.deepCopy(o)
    }

    @Override
    Object assemble(Serializable cached, Object owner) throws HibernateException {
        deepCopy(cached)
    }

    @Override
    Object replace(Object original, Object target, Object owner) throws HibernateException {
        deepCopy(original)
    }
}
