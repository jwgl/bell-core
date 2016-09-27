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

    String getUserName() {
        if(authentication instanceof OAuth2Authentication) {
            authentication.userAuthentication.details.name
        } else {
            authentication.details.name
        }
    }

    String getDepartmentId() {
        if(authentication instanceof OAuth2Authentication) {
            authentication.userAuthentication.details.departmentId
        } else {
            authentication.details.departmentId
        }
    }

    UserType getUserType() {
        if(authentication instanceof OAuth2Authentication) {
            authentication.userAuthentication.details.userType
        } else {
            authentication.details.userType
        }
    }

    String getToken() {
        authentication?.details?.tokenValue
    }

    def getUserRoles() {
        authentication.authorities
                .collect { it.authority }
                .findAll { !it.startsWith("ROLE_PERM") }
    }

    def hasPermission(String perm) {
        authentication.authorities.find { it.authority == "ROLE_$perm" } != null
    }

    def getIpAddress() {
        def request = RequestContextHolder.currentRequestAttributes().request
        request.getHeader("X-Real-IP") ?: request.getHeader("X-Forwarded-For") ?: request.remoteAddr
    }
}
