package cn.edu.bnuz.bell.place

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * 教室借用允许用户类型
 * @author yanglin
 *
 */
class PlaceBookingUserType implements Serializable {
    Place place
    Integer userType

    static belongsTo = [place: Place]

    static mapping = {
        comment  '场地借用允许用户类型'
        table    schema: 'ea'
        id       composite: ['place', 'userType']
        place    comment: '场地'
        userType comment: '用户类型-1:教师;2:学生;9-外部用户'
    }

    boolean equals(other) {
        if (!(other instanceof PlaceBookingUserType)) {
            return false
        }

        other.place?.id == place?.id &&    other.userType == userType
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (place)
            builder.append(place.id)
        if (userType)
            builder.append(userType)
        builder.toHashCode()
    }
}

