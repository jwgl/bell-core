package cn.edu.bnuz.bell.menu.module

import groovy.transform.CompileStatic

/**
 * 菜单
 * Created by yanglin on 2016/9/30.
 */
@CompileStatic
class Menu extends AbstractMenuItem {
    Map<String, String> labels = [:]
    Map<String, AbstractMenuItem> items = [:]

    def getAt(String id) {
        items[id]
    }

    def putAt(String id, AbstractMenuItem item) {
        items[id] = item
    }
}
