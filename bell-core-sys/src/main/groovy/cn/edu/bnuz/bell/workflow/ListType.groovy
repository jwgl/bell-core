package cn.edu.bnuz.bell.workflow

import java.lang.reflect.Field

/**
 * 列表类型。
 * 绑定http小写参数成大写表示，见http://stackoverflow.com/a/21903339
 */
enum ListType {
    TODO("todo"),
    DONE("done"),
    TOBE("tobe"),
    NEXT('next'),
    EXPR('expr'), // 已过期

    private ListType(String val) {
        try {
            Field field = this.class.superclass.getDeclaredField("name")
            field.accessible = true
            field.set(this, val)
            field.accessible = false
        } catch (Exception e) {}
    }
}