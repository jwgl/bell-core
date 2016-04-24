package cn.edu.bnuz.bell.place

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.organization.Department

/**
 * 场地专用单位
 * @author Yang Lin
 */
class PlaceDepartment implements Serializable {
    Place place
    Department department

    static belongsTo = [place: Place]

    static mapping = {
        comment    '场地专用单位'
        table      schema: 'ea'
        id         composite: ['place', 'department']
        place      comment: '场地'
        department comment: '单位'
    }

    boolean equals(other) {
        if (!(other instanceof PlaceDepartment)) {
            return false
        }

        other.place?.id == place?.id &&    other.department?.id == department?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (place)
            builder.append(place.id)
        if (department)
            builder.append(department.id)
        builder.toHashCode()
    }
}
