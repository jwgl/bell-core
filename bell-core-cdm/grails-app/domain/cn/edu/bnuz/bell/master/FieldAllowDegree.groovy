package cn.edu.bnuz.bell.master

import org.codehaus.groovy.util.HashCodeHelper

/**
 * 目录专业允许授予的学位
 * @author Yang Lin
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

        other.field?.id == field?.id && other.discipline?.id == discipline?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, field.id)
        hash = HashCodeHelper.updateHash(hash, discipline.id)
        hash
    }
}
