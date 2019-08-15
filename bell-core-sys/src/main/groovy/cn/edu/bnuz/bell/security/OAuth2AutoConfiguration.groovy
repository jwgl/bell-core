package cn.edu.bnuz.bell.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.common.OAuth2AccessToken

@Configuration
@ConditionalOnClass(OAuth2AccessToken)
@Import([ResourceJwtAccessTokenConverterConfiguration])
class OAuth2AutoConfiguration {
}
