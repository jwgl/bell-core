package cn.edu.bnuz.bell.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.context.request.RequestContextHolder

/**
 * 安全服务
 * @author Yang Lin
 */
class SecurityService {

    Authentication getAuthentication() {
        SCH.context?.authentication
    }

    def getPrincipal() {
        authentication?.principal
    }

    String getUserId() {
        principal
    }

    def getUserDetails() {
        if (authentication instanceof OAuth2Authentication) {
            authentication.userAuthentication.details
        } else {
            authentication.details
        }
    }

    String getUserName() {
        userDetails.name
    }

    String getDepartmentId() {
        userDetails.departmentId
    }

    UserType getUserType() {
        UserType.valueOf(userDetails.userType)
    }

    String getToken() {
        if (authentication instanceof OAuth2Authentication) {
            authentication?.details?.tokenValue
        } else {
            null
        }
    }

    def getUserRoles() {
        authentication.authorities
                .collect { it.authority }
                .findAll { it.startsWith('ROLE_') }
    }

    Set<String> getUserPermissions() {
        authentication.authorities
                .collect { it.authority }
                .findAll { it.startsWith('PERM_') }
                .toSet()
    }

    boolean hasRole(String role) {
        assert role.startsWith('ROLE_')
        authentication.authorities.find { it.authority == role } != null
    }

    boolean hasPermission(String perm) {
        assert perm.startsWith('PERM_')
        authentication.authorities.find { it.authority == perm } != null
    }

    def getIpAddress() {
        def request = RequestContextHolder.currentRequestAttributes().request
        request.getHeader('X-Real-IP') ?: request.getHeader('X-Forwarded-For') ?: request.remoteAddr
    }
}
