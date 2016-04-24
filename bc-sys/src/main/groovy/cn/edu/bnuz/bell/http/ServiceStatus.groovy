package cn.edu.bnuz.bell.http

/**
 * Created by yanglin on 2015/4/13.
 */
enum ServiceStatus {
    /**
     * 操作成功
     */
    OK(200, '成功'),

    /**
     * 资源创建成功
     */
    CREATED(201, '已创建'),

    /**
     * 1.非法参数
     * 2.数据验证不正确
     * 3.资源状态不允许操作
     */
    BAD_REQUEST(400, '错误请求'),

    /**
     * 没有权限
     */
    FORBIDDEN(403, '禁止访问'),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, '未找到')

    final Integer id
    final String name

    private ServiceStatus(int id, String name) {
        this.id = id
        this.name = name
    }
}
