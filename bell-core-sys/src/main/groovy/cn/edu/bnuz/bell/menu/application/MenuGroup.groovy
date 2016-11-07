package cn.edu.bnuz.bell.menu.application

import java.util.concurrent.TimeUnit

/**
 * 菜单组
 * Created by yanglin on 2016/10/2.
 */
class MenuGroup {
    Map<String, Menu> menus = [:]

    MenuGroup(String service, Map<String, Map> menus) {
        menus.each { id, menu ->
            this.menus[id] = new Menu(service, 1, menu)
        }
    }

    synchronized void merge(String service, Map<String, Map> menus) {
        menus.each { id, menu ->
            if (this.menus.containsKey(id)) {
                this.menus[id].merge(service, menu)
            } else {
                this.menus[id] = new Menu(service, 1, menu)
            }
        }
    }

    synchronized void evict(int expiration) {
        long current = System.currentTimeMillis()

        this.menus.entrySet().removeIf { e ->
            TimeUnit.MILLISECONDS.toSeconds(current - e.value.lastUpdate.time) > expiration
        }

        this.menus.each { id, menu ->
            menu.evict(expiration)
        }
    }

    synchronized List getUserMenus(String userId, String userName, String departmentId, Set<String> permissions, Locale locale) {
        List userMenus = []
        this.menus.each { id, menu ->
            def userMenu = menu.getUserMenu(userId, departmentId, permissions, locale)
            if (userMenu) {
                if (userMenu.label == '${userName}') {
                    userMenu.label = userName
                }
                userMenus << userMenu
            }
        }
        userMenus.sort { a, b -> a.order - b.order}
        return userMenus
    }

    String toString() {
        def result = ''
        menus.each { id, menu ->
            result += "\t${id}${menu}"
        }
        result
    }
}
