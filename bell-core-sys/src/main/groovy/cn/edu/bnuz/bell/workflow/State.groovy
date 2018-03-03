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
    STEP1,
    STEP2,
    STEP3,
    STEP4,
    STEP5,
    STEP6,
    STEP7,
    STEP8,
    STEP9,
}
