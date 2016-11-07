package cn.edu.bnuz.bell.menu.application

/**
 * 应用程序菜单
 * Created by yanglin on 2016/10/7.
 */
class ApplicationMenu {
    private Map<String, MenuGroup> menuGroups = [:]

    public void merge(Object moduleMenu, String service) {
        moduleMenu.each {String groupName, Map<String, Map> menus ->
            if (menuGroups.containsKey(groupName)) {
                menuGroups[groupName].merge(service, menus)
            } else {
                menuGroups[groupName] = new MenuGroup(service, menus)
            }
        }
    }

    public void evict(int expiration) {
        menuGroups.each { name, MenuGroup menuGroup ->
            menuGroup.evict(expiration)
        }
    }

    def getUserMenus(String userId, String userName, String departmentId, Set<String> permissions, String groupName, Locale locale) {
        if (menuGroups.containsKey(groupName)) {
            menuGroups[groupName].getUserMenus(userId, userName, departmentId, permissions, locale)
        } else {
            null
        }
    }
}
