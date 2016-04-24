package cn.edu.bnuz.bell.workflow

/**
 * 审核动作
 * @author Yang Lin
 */
enum AuditAction {
    // before workflow
    CREATE(10, '新建'),
    UPDATE(11, '修改'),
    DELETE(12, '删除'),
    // commit
    COMMIT(20, '提交'),
    CANCEL(21, '取消'),
    // review
    ACCEPT(31, '同意'),
    REJECT(32, '退回'),
    REVIEW(33, '加签'),
    // admin
    REVOKE(40, '回收'),
    CLOSE (41, '关闭'),
    OPEN  (42, '打开')

    final Integer id
    final String name

    private AuditAction(int id, String name) {
        this.id = id
        this.name = name
    }

    String toString() {
        name
    }
}
