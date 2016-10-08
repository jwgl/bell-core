package cn.edu.bnuz.bell.menu.module

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 模块菜单自动配置
 * Created by yanglin on 2016/10/5.
 */
@Configuration
class ModuleMenuConfiguration {
    @Bean
    public ModuleMenu moduleMenu() {
        return new ModuleMenu();
    }
}
