package cn.edu.bnuz.bell.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.GrantedAuthority

interface PluginSecurityConfigurer {
    /**
     * 配置用户权限
     * @param user
     * @param authorities
     */
    void configure(User user, Collection<GrantedAuthority> authorities)

    /**
     * 配置HttpSecurity
     */
    void configure(HttpSecurity http)
}
