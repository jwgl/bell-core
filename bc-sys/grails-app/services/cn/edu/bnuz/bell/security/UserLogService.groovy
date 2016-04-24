package cn.edu.bnuz.bell.security

import cn.edu.bnuz.bell.workflow.AuditAction

/**
 * 用户操作日志服务
 * Created by yanglin on 2015/4/7.
 */
class UserLogService {
    SecurityService securityService

    def list(Class clazz, Object item, boolean owner = false) {
        list(clazz.name, normalizeItem(item), owner)
    }

    def list(String module, String item, boolean owner = false) {
        UserLog.executeQuery '''
select new map(
    u.name as user,
    a.action as action,
    a.prevStatus as prevStatus,
    a.nextStatus as nextStatus,
    a.content as content,
    a.dateCreated as date
)
from UserLog a
join a.user u
where a.module = :module
and a.item = :item
and a.action >= :action
order by a.dateCreated desc
''', [module: module, item: item, action: owner ? AuditAction.CREATE : AuditAction.COMMIT]
    }

    def log(AuditAction action, Object item, Object content = null) {
        log(item.getClass(), action, item, content)
    }

    def log(Class clazz, AuditAction action, Object item, Object content = null) {
        UserLog userLog = new UserLog([
            user: User.load(securityService.userId),
            ipAddress: securityService.ipAddress,
            module: clazz.name,
            item: normalizeItem(item),
            action: action.name(),
            content: content?.toString(),
            dateCreated: new Date()
        ])

        userLog.save(failOnError: true, flush: true)
    }

    private String normalizeItem(item) {
        switch (item) {
            case Integer:
            case Long:
                return item.toString()
            case String:
                return item
            default:
                return item?.ident()?.toString()
        }
    }
}
