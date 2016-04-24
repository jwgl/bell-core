package cn.edu.bnuz.bell.master

/**
 * 专业方向
 * @author Yang Lin
 */
class Direction {
    /**
     * 专业方向ID
     * <pre>
     * 201401011
     * --------=
     *   |     |
     *   |     `-- 方向代码
     *   `-------- 专业年级代码，见{@link Major#id}
     * </pre>
     */
    Integer id

    /**
     * 名称
     */
    String name

    static belongsTo = [major : Major]

    static mapping = {
        comment '专业方向'
        table   schema: 'ea'
        id      generator: 'assigned', comment: '专业方向ID'
        name    length: 50, comment: '名称'
        major   comment: '所属专业'
    }
}
