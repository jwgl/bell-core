package cn.edu.bnuz.bell.master

/**
 * 专业类
 * @author Yang Lin
 */
class FieldClass {
    /**
     * 专业类ID
     * <pre>
     * 20120809
     * ----====
     *   | |
     *   | `-- 专业类代码
     *   `-----发布年份
     * </pre>
     */
    Integer id

    /**
     * 代码
     */
    String code

    /**
     * 名称
     */
    String name

    static hasMany = [
        fields: Field
    ]

    static belongsTo = [
        discipline: Discipline    
    ]

    static mapping = {
        comment    '专业类'
        table      schema: 'ea'
        id         generator: 'assigned', comment: '专业类ID'
        code       length: 4, comment: '代码'
        name       length: 10, comment: '名称'
        discipline comment: '学科门类'
    }
}
