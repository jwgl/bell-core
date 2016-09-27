package cn.edu.bnuz.bell.security

/**
 * OAuth client.
 * @author Yang Lin
 */
class OAuthClient {
    String id
    String secret
    String scope
    String autoApproveScopes
    String grantTypes
    String redirectUri
    Integer accessTokenValidity
    Integer refreshTokenValidity


    static mapping = {
        cache                true
        comment              'OAuth客户'
        table                name: 'oauth_client'
        id                   generator: 'assigned', length: 8, comment: '客户ID'
        secret               length: 200, comment: '密码'
        grantTypes           length: 200, comment: '授权类型，逗号分隔'
        scope                length: 200, comment: '授权范围，逗号分隔'
        autoApproveScopes    length: 200, comment: '自动同意的范围，逗号分隔'
        redirectUri          length: 200, comment: '重定义地址'
        accessTokenValidity  length: 200, comment: '权限，逗号分隔'
        refreshTokenValidity length: 200, comment: '资源ID'
    }

    static constraints = {
        autoApproveScopes    nullable: true, maxSize: 200
        redirectUri          nullable: true, maxSize: 200
        accessTokenValidity  nullable: true, maxSize: 200
        refreshTokenValidity nullable: true, maxSize: 200
    }
}
