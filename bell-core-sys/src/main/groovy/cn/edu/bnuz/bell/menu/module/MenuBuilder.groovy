package cn.edu.bnuz.bell.menu.module

import org.grails.io.support.ClassPathResource

/**
 * 菜单构造器
 * Created by yanglin on 2016/9/30.
 */
class MenuBuilder {
    private int indent = 0
    private List stack = []
    public Map<String, MenuGroup> menuGroups = [:]

    def menuGroup(String name, Closure c) {
        if (menuGroups[name]) {
            throw new Exception("MenuGroup name duplicated!")
        }
        MenuGroup menuGroup = new MenuGroup()
        call(menuGroup, c)
        menuGroups[name] = menuGroup
    }

    def invokeMethod(String name, args) {
        AbstractMenuItem item
        String id = name
        switch (args.length) {
            case 1:
                assert parent instanceof Menu
                assert name == Separator.SYMBOL // '---' order
                assert args[0] instanceof Integer
                item = new Separator(
                        order: args[0],
                )
                id = "_s_${item.order}"
                break
            case 2: // label order, {...}
                assert parent instanceof MenuGroup || parent instanceof Menu
                assert args[0] instanceof Integer
                assert args[1] instanceof Closure
                item = new Menu(
                        order: args[0],
                )

                call(item, args[1])
                break
            case 3: // label order, url, perm
                assert parent instanceof Menu
                assert args[0] instanceof Integer
                assert args[1] instanceof String
                assert args[2] instanceof String
                item = new MenuItem(
                        order: args[0],
                        perm:  args[1],
                        url:   args[2],
                )
                break
            default:
                throw new Exception("Bad Config")
        }

        parent[id] = item
    }

    /**
     * 调用闭包。将当前结点压栈，作为下级结点的父结点
     * @param current 当前结点
     * @param c 当前结点的闭包（下级结点）
     */
    private void call(current, Closure c) {
        stack.push(current)
        indent++
        c.delegate = this
        c.call()
        indent--
        stack.pop()
    }

    private getParent() {
        stack.last()
    }

    /**
     * 加载外部文件
     * @param fileName 文件名
     */
    def load(String fileName, Binding binding = null) {
        def input = new ClassPathResource(fileName).inputStream
        Reader reader = new InputStreamReader(input)
        return getShell(binding).evaluate(reader)
    }

    /**
     * 获取GroovyShell
     * @return GroovyShell对象
     */
    private GroovyShell getShell(Binding inBinding) {
        // 绑定顶层函数
        Binding bindings = new Binding([
                menuGroup: this.&menuGroup
        ])

        // 合并绑定
        if (inBinding) {
            bindings.variables.putAll(inBinding.variables)
        }

        return new GroovyShell(this.class.classLoader, bindings)
    }
}
