package cn.edu.bnuz.bell.planning

/**
 * 专业方向
 * @author Yang Lin
 */
class Direction {
    /**
     * 专业方向ID
     * <pre>
     * 2014010101
     * ---------=
     *   |      |
     *   |      `-- 方向代码
     *   `--------- 教学计划号，见{@link cn.edu.bnuz.bell.planning.Program#id}
     * </pre>
     */
    Integer id

    /**
     * 名称
     */
    String name

    static belongsTo = [program : Program]

    static mapping = {
        comment '专业方向'
        table   schema: 'ea'
        id      generator: 'assigned', comment: '专业方向ID'
        name    length: 50, comment: '名称'
        program comment: '所属计划'
    }
}
