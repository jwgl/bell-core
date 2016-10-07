package cn.edu.bnuz.bell.menu.module

import groovy.transform.CompileStatic

/**
 * 菜单项
 * Created by yanglin on 2016/9/30.
 */
@CompileStatic
class MenuItem extends AbstractMenuItem {
    Map<String, String> labels = [:]
    String url
    String perm
}
