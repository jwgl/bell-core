package cn.edu.bnuz.bell.menu.application

/**
 * 抽象菜单项
 * Created by yanglin on 2016/9/30.
 */
abstract class AbstractMenuItem {
    Date lastUpdate
    String service
    int order
    int level

    AbstractMenuItem(String service, int level) {
        this.service = service
        this.level = level
        this.lastUpdate = new Date()
    }

    abstract void merge(String service, Map<String, Object> source)
}
