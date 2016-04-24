package cn.edu.bnuz.bell.master

/**
 *  学科门类
 * @author yanglin
 *
 */
class Discipline {
    /**
     * 学科门类ID
     * <pre>
     * 201208
     * ----==
     *   | |
     *   | `-- 学科门类代码
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
        fieldClasses : FieldClass
    ]

    static mapping = {
        comment '学科门类'
        table   schema: 'ea'
        id      generator: 'assigned', comment: '学科门类ID'
        code    length: 4, comment: '代码'
        name    length: 10, comment: '名称'
    }
}
