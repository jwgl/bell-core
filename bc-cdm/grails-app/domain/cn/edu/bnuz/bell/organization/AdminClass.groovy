package cn.edu.bnuz.bell.organization

import cn.edu.bnuz.bell.master.Major


/**
 * 行政班
 * @author yanglin
 *
 */
class AdminClass {
    /**
     * 行政班ID
     * <pre>
     * 2014010101
     * --------==
     *   |     |
     *   |     `-- 行政班序号
     *   `-------- 专业年级代码，见{@link Major#id}
     * </pre>
     */
    Long id

    /**
     * 名称
     */
    String name

    /**
     * 所属专业
     */
    Major major

    static belongsTo = [department : Department]

    static hasMany = [
        students : Student
    ]

    static mapping = {
        comment    '行政班'
        table      schema: 'ea'
        id         generator: 'assigned', comment: '班级ID'
        name       length:50,     comment: '名称'
        major      comment: '专业'
        department comment: '所属学院'
    }
}
