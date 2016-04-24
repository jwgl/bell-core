package cn.edu.bnuz.bell.system

import cn.edu.bnuz.bell.security.Permission


/**
 * 菜单项
 * @author yanglin
 *
 */
class MenuItem {
    String id
    String name
    String labelCn
    String labelEn
    Integer displayOrder
    String url
    Boolean enabled

    Menu menu
    Permission permission

    static belongsTo = [menu: Menu]

    static mapping = {
        comment       '菜单项'
        id           length: 100, generator: 'assigned', comment: '菜单项ID'
        name         length: 50, comment: '名称'
        labelCn      length: 50, comment: '中文'
        labelEn      length: 50, comment: '英文'
        displayOrder comment: '显示顺序'
        url          length: 100, comment: '地址'
        enabled      defaultValue: "true", comment: '是否可用'
        menu         comment: '所属菜单'
        permission   comment: '权限'
    }

    static constraints = {
        permission   nullable: true
    }
}
