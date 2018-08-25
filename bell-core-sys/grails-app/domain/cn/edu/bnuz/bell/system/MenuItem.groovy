package cn.edu.bnuz.bell.system

import cn.edu.bnuz.bell.orm.PostgreSQLStringArrayUserType
import cn.edu.bnuz.bell.security.Permission

/**
 * 菜单项
 * @author Yang Lin
 *
 */

class MenuItem {
    String id
    String label
    Integer displayOrder
    String url
    Boolean enabled
    String[] dependsOn

    Menu menu
    Permission permission

    static belongsTo = [menu: Menu]

    static mapping = {
        comment       '菜单项'
        id           type: 'text', generator: 'assigned', comment: '菜单项ID'
        label        type: 'text', comment: '显示标签'
        displayOrder comment: '显示顺序'
        url          type: 'text', comment: '地址'
        enabled      defaultValue: "true", comment: '是否可用'
        dependsOn    type: PostgreSQLStringArrayUserType, comment: '依赖的应用'
        menu         comment: '所属菜单'
        permission   comment: '权限'
    }

    static constraints = {
        permission   nullable: true
    }
}
