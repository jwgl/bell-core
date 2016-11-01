package cn.edu.bnuz.bell.orm

import groovy.transform.CompileStatic
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.usertype.UserType

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

@CompileStatic
abstract class PostgreSQLEnumUserType implements UserType {
    abstract Class<? extends Enum> getEnumClass();

    @Override
    public int[] sqlTypes() {
        [Types.OTHER] as int[]
    }

    @Override
    Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0])
        if (!value) {
            return null
        } else {
            return Enum.valueOf(enumClass, value)
        }
    }

    @Override
    void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        st.setObject(index, value, Types.OTHER)
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Enum) value
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode()
    }

    @Override
    public boolean isMutable() {
        return false
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original
    }

    @Override
    public Class returnedClass() {
        return enumClass
    }
}
