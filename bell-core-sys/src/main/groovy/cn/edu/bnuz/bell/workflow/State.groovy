package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
enum State {
    CREATED,
    SUBMITTED,
    CHECKED,
    REJECTED,
    APPROVED,
    CLOSED,
    REVOKED,
    PROGRESS,
    FINISHED,
    DELETED,
}
