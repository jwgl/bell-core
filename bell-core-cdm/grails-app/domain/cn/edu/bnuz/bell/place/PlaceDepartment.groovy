package cn.edu.bnuz.bell.place

import cn.edu.bnuz.bell.organization.Department

/**
 * 场地专用单位
 * @author Yang Lin
 */
class PlaceDepartment implements Serializable {
    /**
     * 教学场地
     */
    Place place

    /**
     * 单位
     */
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

        other.place?.id == place?.id && other.department?.id == department?.id
    }

    int hashCode() {
        Objects.hash(place.id, department.id)
    }
}
