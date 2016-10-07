package cn.edu.bnuz.bell.menu.application

/**
 * 分隔符
 * Created by yanglin on 2016/9/30.
 */
class Separator extends AbstractMenuItem {
    static final SYMBOL = '---'

    public Separator(String service, int level, Map item) {
        super(service, level)
        this.order = item.order
    }

    void merge(String service, Map item) {
        this.lastUpdate = new Date()
        this.service = service
        this.order = item.order
    }

    String toString() {
        ">${service}\n"
    }
}
