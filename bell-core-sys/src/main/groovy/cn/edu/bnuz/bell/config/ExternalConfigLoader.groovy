package cn.edu.bnuz.bell.config

import grails.io.IOUtils
import groovy.util.logging.Slf4j
import org.grails.core.io.DefaultResourceLocator
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertiesPropertySource

/**
 * 外部配置加载器
 */
@Slf4j
class ExternalConfigLoader {
    static load(Environment environment) {
        def location = environment.getProperty('BELL_CONFIG_LOCATION') ?: environment.getProperty('bell.config.location', "/etc/bell")
        def appName = "${environment.getProperty('info.app.name')}"

        def extensions = ['yml', 'groovy']
        String[] parts = appName.split('-')
        for (def i = parts.length - 1; i >= 0; i--) {
            def subParts = parts[0..i]
            extensions.each { ext ->
                File file = new File(location, "${subParts.join('-')}.${ext}")
                if (!file.exists()) {
                    return
                }
                def source = loadFile(file, ext)
                (environment as ConfigurableEnvironment).propertySources.addFirst(source)
            }
        }
    }

    private static MapPropertySource loadFile(File file, String ext) {
        log.debug "Load external config from ${file.absolutePath}"

        DefaultResourceLocator resourceLocator = new DefaultResourceLocator()
        def configLocation = file.toURI().toString()

        def configurationResource = resourceLocator.findResourceForURI(configLocation)
        switch (ext.toLowerCase()) {
            case 'yml':
                YamlPropertiesFactoryBean ypfb = new YamlPropertiesFactoryBean()
                ypfb.setResources(configurationResource)
                ypfb.afterPropertiesSet()
                Properties properties = ypfb.getObject()
                return new PropertiesPropertySource(configLocation, properties)
                break
            case 'groovy':
                def config = new ConfigSlurper(grails.util.Environment.current.name).parse(IOUtils.toString(configurationResource.getInputStream(), 'UTF-8'))
                return new MapPropertySource(configLocation, config)
                break
        }
    }
}
