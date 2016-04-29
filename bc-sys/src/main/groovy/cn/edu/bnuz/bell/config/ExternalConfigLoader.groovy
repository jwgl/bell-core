package cn.edu.bnuz.bell.config

import grails.io.IOUtils
import org.grails.core.io.DefaultResourceLocator
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertiesPropertySource

/**
 * 外部配置加载器
 */
class ExternalConfigLoader {
    static load(Environment environment) {
        def location = environment.getProperty('BELL_CONFIG_LOCATION') ?: environment.getProperty('bell.config.location', "/etc/bell/conf")
        def appName = "${environment.getProperty('info.app.name')}"

        def extensions = ['yml', 'groovy']
        extensions.each { ext ->
            File file = new File(location, "${appName}.${ext}")
            if (!file.exists()) {
                return
            }

            DefaultResourceLocator resourceLocator = new DefaultResourceLocator()
            def configLocation = file.toURI().toString()
            println configLocation
            def configurationResource = resourceLocator.findResourceForURI(configLocation)
            MapPropertySource source
            switch (ext.toLowerCase()) {
                case 'yml':
                    YamlPropertiesFactoryBean ypfb = new YamlPropertiesFactoryBean()
                    ypfb.setResources(configurationResource)
                    ypfb.afterPropertiesSet()
                    Properties properties = ypfb.getObject()
                    source = new PropertiesPropertySource(configLocation, properties)
                    break
                case 'groovy':
                    def config = new ConfigSlurper(grails.util.Environment.current.name).parse(IOUtils.toString(configurationResource.getInputStream(), 'UTF-8'))
                    source = new MapPropertySource(configLocation, config)
                    break
                default:
                    return
            }
            (environment as ConfigurableEnvironment).propertySources.addFirst(source)
        }
    }
}
