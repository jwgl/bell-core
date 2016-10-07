package cn.edu.bnuz.bell.menu.application

import groovy.json.StringEscapeUtils

/**
 * 菜单项
 * Created by yanglin on 2016/10/2.
 */
class MenuItem extends AbstractMenuItem {
    Map<String, String> labels = [:]
    String url
    String perm

    MenuItem(String service, int level, Map item) {
        super(service, level)
        this.order = item.order
        this.url = item.url
        this.perm = item.perm

        item.labels.each { String lang, String label ->
            this.labels[lang] = StringEscapeUtils.unescapeJava(label)
        }
    }

    @Override
    void merge(String service, Map item) {
        this.lastUpdate = new Date()
        this.service = service
        this.order = item.order
        this.url = item.url
        this.perm = item.perm

        item.labels.each { String lang, String label ->
            this.labels[lang] = StringEscapeUtils.unescapeJava(label)
        }
    }

    String toString() {
        "(${labels})[${order}]>${service}: ${url} ${perm}\n"
    }
}

