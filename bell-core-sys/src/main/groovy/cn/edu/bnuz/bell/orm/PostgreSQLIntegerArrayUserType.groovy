package cn.edu.bnuz.bell.orm

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.usertype.UserType

import java.sql.*

class PostgreSQLIntegerArrayUserType implements UserType {
    public static final PostgreSQLIntegerArrayUserType INSTANCE = new PostgreSQLIntegerArrayUserType();
    static final int SQL_TYPE = 90001

    @Override
    int[] sqlTypes() {
        [SQL_TYPE] as int[]
    }

    @Override
    Class<Integer[]> returnedClass() {
        Integer[]
    }

    @Override
    boolean equals(Object o1, Object o2) throws HibernateException {
        if (o1 == null && o2 == null) {
            true
        } else if (o1 == null || o2 == null) {
            false
        } else if (o1 instanceof int[] && o2 instanceof int[]) {
            Arrays.equals(o1, o2)
        } else if (o1 instanceof Integer[] && o2 instanceof Integer[]) {
            Arrays.equals(o1, o2)
        } else {
            false
        }
    }

    @Override
    Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (strings.length != 1)
            throw new IllegalArgumentException("strings.length != 1, strings = " + strings)

        Array value = resultSet.getArray(strings[0])

        if (value == null) {
            return null
        } else {
            return value.getArray()
        }
    }

    @Override
    void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.ARRAY)
        } else if (o instanceof Integer[]) {
            Array inArray = preparedStatement.getConnection().createArrayOf("integer", o);
            preparedStatement.setArray(i, inArray)
        } else if (o instanceof int[]) {
            Array inArray = preparedStatement.getConnection().createArrayOf("integer", wrap(o))
            preparedStatement.setArray(i, inArray)
        } else {
            throw new IllegalArgumentException("Invalid type of input: " + o.class.name)
        }
    }

    @Override
    Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            null
        } else if (o instanceof Integer[] || o instanceof int[]) {
            o.clone()
        } else {
            throw new IllegalArgumentException("Invalid type to copy: " + o.class.name)
        }
    }

    @Override
    boolean isMutable() {
        return true
    }

    @Override
    int hashCode(Object o) throws HibernateException {
        return o.hashCode()
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

    private static Object[] wrap(int[] intArray) {
        Integer[] result = new Integer[intArray.length]
        for (int i = 0; i < intArray.length; i++) {
            result[i] = Integer.valueOf(intArray[i])
        }
        result
    }
}