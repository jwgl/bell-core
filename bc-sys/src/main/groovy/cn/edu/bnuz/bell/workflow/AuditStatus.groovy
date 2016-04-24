package cn.edu.bnuz.bell.workflow

/**
 * 审核状态
 */
enum AuditStatus {
    NULL     (-1, "空"),
    CREATED  (0, "新建"),
    COMMITTED(1, "待审核"),
    CHECKED  (2, "待审批"),
    REJECTED (3, "退回"),
    APPROVED (4, "已审批"),
    CLOSED   (5, "已关闭"),
    REVOKED  (6, "已回收"),
    DELETED  (9, "已删除"),

    final Integer id
    final String name

    private AuditStatus(int id, String name) {
        this.id = id
        this.name = name
    }

    private static stateMatrix = [
            (CREATED)  : [
                    (AuditAction.UPDATE):       CREATED,
                    (AuditAction.DELETE):       DELETED,
                    (AuditAction.COMMIT):       COMMITTED,
            ],
            (COMMITTED): [
                    (AuditAction.CANCEL):       CREATED,
                    (AuditAction.ACCEPT):       CHECKED,
                    (AuditAction.REJECT):       REJECTED,
            ],
            (CHECKED)  : [
                    (AuditAction.ACCEPT):       APPROVED,
                    (AuditAction.REJECT):       REJECTED,
            ],
            (REJECTED) : [
                    (AuditAction.UPDATE):       CREATED,
                    (AuditAction.DELETE):       DELETED,
                    (AuditAction.COMMIT):       COMMITTED,
            ],
            (APPROVED) : [:],
            (CLOSED)   : [:],
            (REVOKED)  : [:],
            (DELETED)  : [:],
    ]

    private static adminMatrix = [
            (CREATED) : [:],
            (COMMITTED): [
                    (AuditAction.CLOSE):        CLOSED
            ],
            (CHECKED)  : [
                    (AuditAction.CLOSE):        CLOSED
            ],
            (REJECTED) : [:],
            (APPROVED) : [
                    (AuditAction.REVOKE):       REVOKED
            ],
            (CLOSED)  : [
                    (AuditAction.DELETE):       DELETED,
            ],
            (REVOKED)  : [
                    (AuditAction.DELETE):       DELETED,
            ],
            (DELETED)  : [:],
    ]

    boolean allow(AuditAction action) {
        stateMatrix[this].containsKey(action)
    }

    AuditStatus next(AuditAction action) {
        stateMatrix[this][action]?: this
    }

    boolean allowAdmin(AuditAction action) {
        adminMatrix[this].containsKey(action)
    }

    AuditStatus nextAdmin(AuditAction action) {
        adminMatrix[this][action]?: this
    }

    String toString() {
        name
    }
}
