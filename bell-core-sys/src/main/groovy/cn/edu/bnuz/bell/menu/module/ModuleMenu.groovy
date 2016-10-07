package cn.edu.bnuz.bell.menu.module

import com.netflix.appinfo.ApplicationInfoManager
import grails.web.context.ServletContextHolder
import groovy.json.JsonBuilder
import groovy.json.StringEscapeUtils
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource

import javax.annotation.PostConstruct

/**
 * 模块菜单服务
 */
@Slf4j
@CompileStatic
class ModuleMenu {
    private LOCALES = [
            Locale.ENGLISH,
            Locale.SIMPLIFIED_CHINESE,
    ]

    @Value('${bell.module.menu:menu.groovy}')
    String moduleMenu

    @Autowired
    MessageSource messageSource

    @Autowired
    ApplicationInfoManager applicationInfoManager

    @PostConstruct
    private init() {
        log.debug('Load menu: ' + moduleMenu)
        try {
            def menus = load(moduleMenu)
            applicationInfoManager.registerAppMetadata([moduleMenu: new JsonBuilder(menus).toString()])
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    /**
     * 加载模块菜单，菜单文件位于grails-app/conf目录中，缺省文件名为menu.groovy
     * @param fileName 菜单文件
     * @return 菜单
     */
    Map<String, MenuGroup> load(String fileName) {
        MenuBuilder builder = new MenuBuilder()
        builder.load(fileName)
        fillLabel(builder.menuGroups)
        return builder.menuGroups
    }

    /**
     * 填充菜单项的label信息
     * @param menuGroups 菜单
     */
    private void fillLabel(Map<String, MenuGroup> menuGroups) {
        menuGroups.each { groupName, menuGroup ->
            def prefix = "menu.${groupName}"
            menuGroup.each { id, menu ->
                fillLabel(prefix, id, menu)
            }
        }
    }

    /**
     * 填充菜单项的label信息
     * @param prefix 前缀
     * @param ami 菜单项
     */
    private void fillLabel(String prefix, String id, AbstractMenuItem ami) {
        def code = "${prefix}.${id}"
        if (ami instanceof Menu) {
            def labels = ami.labels
            LOCALES.each { Locale locale->
                labels[locale.toString()] = StringEscapeUtils.escapeJava(messageSource.getMessage(code, null, code, locale))
            }
            ami.items.each { itemId, item ->
                fillLabel(code, itemId, item)
            }
        } else if (ami instanceof MenuItem) {
            def labels = ami.labels
            LOCALES.each { Locale locale->
                labels[locale.toString()] = StringEscapeUtils.escapeJava(messageSource.getMessage(code, null, code, locale))
            }
        }
    }
}
