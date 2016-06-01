package cn.edu.bnuz.bell.system

/**
 * 菜单视图
 * @author Yang Lin
 */
class MenuDto {
    String id
    String name
    String labelCn
    String labelEn
    Integer menuLevel
    String root

    static mapping = {
        table name: 'dv_menu'
    }
}
