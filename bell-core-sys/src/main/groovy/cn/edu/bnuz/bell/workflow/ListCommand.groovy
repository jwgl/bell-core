package cn.edu.bnuz.bell.workflow

import grails.validation.Validateable

class ListCommand implements Validateable {
    private Integer max
    private Integer offset

    Integer getMax() {
        this.max ?: this.offset ? 20 : Integer.MAX_VALUE
    }

    void setMax(Integer max) {
        this.max = max
    }

    Integer getOffset() {
        this.offset ?: 0
    }

    void setOffset(Integer offset) {
        this.offset = offset
    }

    ListType type
    String query

    Map getArgs() {
       [offset: getOffset(), max: getMax()]
    }
}
