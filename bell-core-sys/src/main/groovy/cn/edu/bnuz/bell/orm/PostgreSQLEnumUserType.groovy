package cn.edu.bnuz.bell.orm

import groovy.transform.CompileStatic
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.usertype.UserType

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

@CompileStatic
abstract class PostgreSQLEnumUserType implements UserType {
    abstract Class<? extends Enum> getEnumClass()

    @Override
    int[] sqlTypes() {
        [Types.OTHER] as int[]
    }

    @Override
    Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0])
        if (!value) {
            return null
        } else {
            return Enum.valueOf(enumClass, value)
        }
    }

    @Override
    void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        st.setObject(index, value, Types.OTHER)
    }

    @Override
    Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached
    }

    @Override
    Object deepCopy(Object value) throws HibernateException {
        return value
    }

    @Override
    Serializable disassemble(Object value) throws HibernateException {
        return (Enum) value
    }

    @Override
    boolean equals(Object x, Object y) throws HibernateException {
        return x == y
    }

    @Override
    int hashCode(Object x) throws HibernateException {
        return x.hashCode()
    }

    @Override
    boolean isMutable() {
        return false
    }

    @Override
    Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original
    }

    @Override
    Class returnedClass() {
        return enumClass
    }
}
