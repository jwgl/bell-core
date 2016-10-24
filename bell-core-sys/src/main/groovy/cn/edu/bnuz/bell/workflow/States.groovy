package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
enum States {
    CREATED(0, "新建"),
    COMMITTED(1, "待审核"),
    CHECKED(2, "待审批"),
    REJECTED(3, "退回"),
    APPROVED(4, "已审批"),
    CLOSED(5, "已关闭"),
    REVOKED(6, "已回收"),
    DELETED(9, "已删除"),

    final Integer id
    final String name

    private States(int id, String name) {
        this.id = id
        this.name = name
    }
}
