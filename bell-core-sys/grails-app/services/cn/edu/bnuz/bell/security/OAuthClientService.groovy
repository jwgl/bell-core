package cn.edu.bnuz.bell.security

import grails.gorm.transactions.Transactional
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException

/**
 * OAuthClient服务
 * @author Yang Lin
 */
class OAuthClientService implements ClientDetailsService {
    Map<String, OAuthClientDetails> cache = [:]

    @Override
    @Transactional(readOnly=true)
    OAuthClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        def details = cache[clientId]
        if (!details) {
            OAuthClient oauthClient = OAuthClient.get(clientId)
            if(oauthClient) {
                details = new OAuthClientDetails(oauthClient)
                cache[clientId] = details
            } else {
                throw new ClientRegistrationException("No client with requested id: $clientId")
            }
        }
        return details
    }
}
