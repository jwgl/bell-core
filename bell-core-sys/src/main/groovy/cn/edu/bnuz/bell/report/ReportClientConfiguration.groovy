package cn.edu.bnuz.bell.report

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestOperations
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails

/**
 * 报表客户端配置
 */
@Configuration
class ReportClientConfiguration {
    @LoadBalanced
    @Bean('reportRestTemplate')
    OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext,
                                      OAuth2ProtectedResourceDetails details) {
        new OAuth2RestTemplate(details, oauth2ClientContext)
    }
}
