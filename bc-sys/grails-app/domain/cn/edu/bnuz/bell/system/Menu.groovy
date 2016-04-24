package cn.edu.bnuz.bell.system

/**
 * 菜单
 * @author yanglin
 *
 */
class Menu {
    String id
    String name
    String labelCn
    String labelEn
    Integer displayOrder

    static hasMany = [
        items: MenuItem
    ]

    static mapping = {
        comment      '菜单'
        id           length: 100, generator: 'assigned', comment: '菜单ID'
        name         length: 50, comment: '名称'
        labelCn      length: 50, comment: '中文'
        labelEn      length: 50, comment: '英文'
        displayOrder comment: '显示顺序'
    }
}
