package cn.edu.bnuz.bell.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@Configuration
@Conditional(OAuth2ResourceServerConfiguration.ResourceServerCondition.class)
@ConditionalOnClass([ EnableResourceServer.class, SecurityProperties.class ])
class ResourceJwtAccessTokenConverterConfiguration {
    @Bean
    JwtAccessTokenConverterConfigurer jwtAccessTokenConverterConfigurer() {
        new JwtAccessTokenConverterConfigurer() {
            @Override
            void configure(JwtAccessTokenConverter converter) {
                (converter.accessTokenConverter as DefaultAccessTokenConverter).setUserTokenConverter(new DefaultUserAuthenticationConverter() {
                    Authentication extractAuthentication(Map<String, ?> map) {
                        UsernamePasswordAuthenticationToken authentication = super.extractAuthentication(map) as UsernamePasswordAuthenticationToken
                        authentication.details = map.get('details')
                        return authentication
                    }
                })
            }
        }
    }
}
