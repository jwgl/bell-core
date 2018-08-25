package cn.edu.bnuz.bell.system

import org.codehaus.groovy.util.HashCodeHelper

class LocaleLabel implements Serializable {
    String labelId
    String locale
    String value

    static mapping = {
        comment  '本地化标签'
        id       composite: ['labelId', 'locale']
        labelId  type: 'text', comment: '标签ID'
        locale   type: 'text', comment: '地区'
        value    type: 'text', comment: '标签值'
    }

    boolean equals(other) {
        if (!(other instanceof LocaleLabel)) {
            return false
        }

        other.labelId == labelId && other.locale == locale
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, labelId)
        hash = HashCodeHelper.updateHash(hash, locale)
        hash
    }
}
