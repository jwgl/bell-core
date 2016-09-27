package cn.edu.bnuz.bell.security

import grails.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException

/**
 * OAuthClient服务
 * @author Yang Lin
 */
class OAuthClientService implements ClientDetailsService {
    @Override
    @Transactional(readOnly=true, noRollbackFor=[IllegalArgumentException, UsernameNotFoundException])
    OAuthClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClient oauthClient = OAuthClient.get(clientId)
        if(oauthClient) {
            new OAuthClientDetails(oauthClient)
        } else {
            throw new ClientRegistrationException("No client with requested id: $clientId")
        }
    }
}
