package cn.edu.bnuz.bell.place

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.master.Term

/**
 * 教室允许借用学期
 * @author yanglin
 *
 */
class PlaceBookingTerm implements Serializable {
    Place place
    Term term

    static belongsTo = [place: Place]

    static mapping = {
        comment '场地允许借用学期'
        table   schema: 'ea'
        id      composite: ['place', 'term']
        place   comment: '场地'
        term    comment: '学期'
    }

    boolean equals(other) {
        if (!(other instanceof PlaceBookingTerm)) {
            return false
        }

        other.place?.id == place?.id &&    other.term?.id == term?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (place)
            builder.append(place.id)
        if (term)
            builder.append(term.id)
        builder.toHashCode()
    }
}
