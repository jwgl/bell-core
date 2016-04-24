package cn.edu.bnuz.bell.master

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * 目录专业允许授予的学位
 * @author yanglin
 *
 */
class FieldAllowDegree implements Serializable{
    Discipline discipline

    static belongsTo = [field: Field]

    static mapping = {
        comment    '目录专业允许授予学位'
        table      schema: 'ea'
        id         composite: ['field', 'discipline']
        field      comment: '目录专业'
        discipline comment: '学位'
    }

    boolean equals(other) {
        if (!(other instanceof FieldAllowDegree)) {
            return false
        }

        other.field?.id == field?.id &&    other.discipline?.id == discipline?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (field)
            builder.append(field.id)
        if (discipline)
            builder.append(discipline.id)
        builder.toHashCode()
    }
}
