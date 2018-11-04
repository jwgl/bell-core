package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
enum Event {
    CREATE,
    UPDATE,
    DELETE,
    SUBMIT,
    CANCEL,
    ACCEPT,
    REJECT,
    REVIEW,
    REVOKE,
    CLOSE,
    OPEN,
    PROCESS,
    FINISH,
    NEXT,
    APPROVE,
}
