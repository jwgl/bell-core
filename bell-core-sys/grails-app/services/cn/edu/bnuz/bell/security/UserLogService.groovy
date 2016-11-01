package cn.edu.bnuz.bell.security
/**
 * 用户操作日志服务
 * @author Yang Lin
 */
class UserLogService {
    def log(String userId, String ipAddress, String event, Object item, Object content = null) {
        log(userId, ipAddress, item.getClass(), event, item, content)
    }

    def log(String userId, String ipAddress, Class clazz, String event, Object item, Object content = null) {
        UserLog userLog = new UserLog([
            user: User.load(userId),
            ipAddress: ipAddress,
            module: clazz.name,
            item: normalizeItem(item),
            event: event,
            content: content?.toString(),
            dateCreated: new Date()
        ])

        userLog.save()
    }

    private static String normalizeItem(Object item) {
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
