package cn.edu.bnuz.bell.workflow

import groovy.transform.CompileStatic

@CompileStatic
enum States {
    CREATED  (0,  '新建'),
    COMMITTED(1,  '待审核'),
    CHECKED  (2,  '待审批'),
    REJECTED (3,  '退回'),
    APPROVED (4,  '已审批'),
    CLOSED   (5,  '已关闭'),
    REVOKED  (6,  '已回收'),
    PROGRESS (7,  '处理中'),
    FINISHED (8,  '已完成'),
    DELETED  (9,  '已删除'),
    STEP1    (11, '步骤1'),
    STEP2    (12, '步骤2'),
    STEP3    (13, '步骤3'),
    STEP4    (14, '步骤4'),
    STEP5    (15, '步骤5'),
    STEP6    (16, '步骤6'),
    STEP7    (17, '步骤7'),
    STEP8    (18, '步骤8'),
    STEP9    (19, '步骤9'),

    final Integer id
    final String name

    private States(int id, String name) {
        this.id = id
        this.name = name
    }

    String toString() {
        name
    }
}
