package cn.edu.bnuz.bell.security

import groovy.transform.CompileStatic
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.util.StringUtils

/**
 * OAuth Client Details
 * @author Yang Lin
 */
@CompileStatic
class OAuthClientDetails implements ClientDetails {
    OAuthClientDetails(OAuthClient client) {
        this.clientId = client.id
        this.resourceIds = new HashSet()
        this.clientSecret = client.secret
        this.scope = StringUtils.commaDelimitedListToSet(client.scope)
        this.authorizedGrantTypes = StringUtils.commaDelimitedListToSet(client.grantTypes)
        this.registeredRedirectUri = new HashSet()
        this.authorities = new HashSet()
        this.accessTokenValiditySeconds = client.accessTokenValidity
        this.refreshTokenValiditySeconds = client.refreshTokenValidity
        this.autoApproveScopes = StringUtils.commaDelimitedListToSet(client.autoApproveScopes)
        this.additionalInformation = new HashMap()
    }

    String clientId

    Set<String> resourceIds

    boolean isSecretRequired() {
        true
    }

    String clientSecret

    boolean isScoped() {
        this.scope && this.scope.size() > 0
    }

    Set<String> scope

    Set<String> authorizedGrantTypes

    Set<String> registeredRedirectUri

    Collection<GrantedAuthority> authorities

    Integer accessTokenValiditySeconds

    Integer refreshTokenValiditySeconds

    Set<String> autoApproveScopes

    boolean isAutoApprove(String scope) {
        this.autoApproveScopes && this.autoApproveScopes.contains(scope)
    }

    Map<String, Object> additionalInformation
}
