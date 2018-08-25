package cn.edu.bnuz.bell.system

/**
 * 菜单
 * @author Yang Lin
 */
class Menu {
    String id
    String label
    Integer displayOrder

    static hasMany = [
        items: MenuItem
    ]

    static mapping = {
        comment      '菜单'
        id           type: 'text', generator: 'assigned', comment: '菜单ID'
        label        type: 'text', comment: '名称'
        displayOrder comment: '显示顺序'
    }
}
