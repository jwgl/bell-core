package cn.edu.bnuz.bell.menu.module

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 模块菜单配置
 */
@Configuration
class ModuleMenuConfiguration {
    @Bean
    ModuleMenu moduleMenu() {
        return new ModuleMenu()
    }
}
