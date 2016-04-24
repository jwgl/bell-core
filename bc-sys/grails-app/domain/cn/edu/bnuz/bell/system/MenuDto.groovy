package cn.edu.bnuz.bell.system

class MenuDto {
    String id
    String name
    String labelCn
    String labelEn
    Integer menuLevel
    String root

    static mapping = {
        table name: 'v_menu'
    }
}
