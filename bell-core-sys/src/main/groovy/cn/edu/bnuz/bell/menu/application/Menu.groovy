package cn.edu.bnuz.bell.menu.application

import groovy.json.StringEscapeUtils

import java.util.concurrent.TimeUnit

/**
 * 菜单
 * Created by yanglin on 2016/9/30.
 */
class Menu extends AbstractMenuItem {
    Map<String, String> labels = [:]
    Map<String, AbstractMenuItem> items = [:]

    Menu(String service, int level, Map menu) {
        super(service, level)

        this.order = menu.order
        this.level = level

        menu.labels.each { String lang, String label ->
            this.labels[lang] = StringEscapeUtils.unescapeJava(label)
        }

        if (menu.items) {
            menu.items.each { String id, item ->
                items[id] = createItem(id, item)
            }
        }
    }

    void merge(String service, Map menu) {
        this.lastUpdate = new Date()
        this.service = service
        this.order = menu.order

        menu.labels.each { String lang, String label ->
            this.labels[lang] = StringEscapeUtils.unescapeJava(label)
        }

        if (menu.items) {
            menu.items.each { String id, Map item ->
                if (items.containsKey(id)) {
                    items[id].merge(service, item)
                } else {
                    items[id] = createItem(id, item)
                }
            }
        }
    }

    def getUserMenu(String userId, Set<String> permissions, Locale locale) {
        def labelKey = locale.toString()

        // Remove locale's script part, like zh_CN_#Hans
        if (labelKey.contains('_#')) {
            labelKey = labelKey.replaceAll('_#.*', '')
        }

        if (!labels[labelKey]) {
            labelKey = Locale.ENGLISH.toString()
        }

        def userMenu = [
                label: labels[labelKey],
                order: order,
                items: []
        ]

        this.items.each { String id, item ->
            if (item instanceof Separator) {
                userMenu.items << [
                        label: Separator.SYMBOL,
                        order: item.order,
                ]
            } else if (item instanceof MenuItem) {
                if (permissions.contains(item.perm)) {
                    userMenu.items << [
                            label: item.labels[labelKey],
                            order: item.order,
                            url  : item.url.replace('${userId}', userId)
                    ]
                }
            } else if (item instanceof Menu) {
                def menu = item.getUserMenu(userId, permissions, locale)
                if (menu) {
                    userMenu.items << menu
                }
            }
        }

        if (userMenu.items.size() > 0) {
            userMenu.items.sort { a, b -> a.order - b.order }
            // 删除连续的Separator
            for (def i = 0; i < userMenu.items.size() - 1; i++) {
                if (userMenu.items[i].label == Separator.SYMBOL && userMenu.items[i + 1].label == Separator.SYMBOL) {
                    userMenu.items.removeAt(i + 1)
                }
            }
            // 删除开始和结束的Separator
            if (userMenu.items[0].label == Separator.SYMBOL) {
                userMenu.items.removeAt(0)
            }
            if (userMenu.items[userMenu.items.size() - 1].label == Separator.SYMBOL) {
                userMenu.items.removeAt(userMenu.items.size() - 1)
            }

            if (userMenu.items.size() > 0) {
                return userMenu
            }
        }
        return null
    }

    private AbstractMenuItem createItem(String id, Map item) {
        if (id.startsWith('_s_')) {
            return new Separator(service, level + 1, item)
        } else if (item.items) {
            return new Menu(service, level + 1, item)
        } else {
            return new MenuItem(service, level + 1, item)
        }
    }

    def evict(int expiration) {
        long current = System.currentTimeMillis()

        this.items.entrySet().removeIf { e ->
            TimeUnit.MILLISECONDS.toSeconds(current - e.value.lastUpdate.time) > expiration
        }

        this.items.each { id, item ->
            if (item instanceof Menu) {
                item.evict(expiration)
            }
        }
    }

    String toString() {
        String result = "(${labels})[$order]>${service}:\n"
        items.each { id, item ->
            result += "${'\t' * (level + 1)}${id}${item}"
        }
        result
    }
}
