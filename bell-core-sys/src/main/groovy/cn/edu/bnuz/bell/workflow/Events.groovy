package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
enum Events {
    CREATE,
    UPDATE,
    DELETE,
    // commit
    COMMIT,
    CANCEL,
    // review
    ACCEPT,
    REJECT,
    REVIEW,
    // admin
    REVOKE,
    CLOSE,
    OPEN,
    // progress
    PROCESS,
    FINISH,
}
