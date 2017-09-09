package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.organization.AdminClass
import org.codehaus.groovy.util.HashCodeHelper

/**
 * 排课板块-行政班
 */
class TimeplateAdminClass implements Serializable {
    Timeplate timeplate
    AdminClass adminClass

    static belongsTo = [timeplate: Timeplate]

    static mapping = {
        comment    '排课板块-行政班'
        table      schema: 'ea'
        id         composite: ['timeplate', 'adminClass']
        timeplate  comment: '排课板块'
        adminClass comment: '行政班'
    }

    boolean equals(other) {
        if (!(other instanceof TimeplateAdminClass)) {
            return false
        }

        other.timeplate?.id == timeplate?.id && other.adminClass?.id == adminClass?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, timeplate.id)
        hash = HashCodeHelper.updateHash(hash, adminClass.id)
        hash
    }
}
