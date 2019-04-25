package cn.edu.bnuz.bell.place

import cn.edu.bnuz.bell.master.Term

/**
 * 场地借用-允许学期
 * @author Yang Lin
 */
class PlaceBookingTerm implements Serializable {
    /**
     * 教学场地
     */
    Place place

    /**
     * 学期
     */
    Term term

    static belongsTo = [place: Place]

    static mapping = {
        comment '场地借用-允许学期'
        table   schema: 'ea'
        id      composite: ['place', 'term']
        place   comment: '场地'
        term    comment: '学期'
    }

    boolean equals(other) {
        if (!(other instanceof PlaceBookingTerm)) {
            return false
        }

        other.place?.id == place?.id && other.term?.id == term?.id
    }

    int hashCode() {
        Objects.hash(place.id, term.id)
    }
}
