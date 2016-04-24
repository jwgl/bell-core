package cn.edu.bnuz.bell.master

/**
 * 课程项目
 * @author yanglin
 *
 */
class CourseItem {
    /**
     * 课程项目ID
     */
    String id

    /**
     * 名称
     */
    String name

    /**
     * 序号
     */
    Integer ordinal

    /**
     * 是否主项目
     * 对于项目关系为and的课程，需要指定其中一个项目为主项目。
     * 其它情况为false。
     */
    Boolean isPrimary

    static belongsTo = [course: Course]

    static mapping = {
        comment   '课程项目'
        table     schema: 'ea'
        id        generator: 'assigned', length: 10, comment: '课程项目ID'
        name      length:50,    comment: '名称'
        ordinal   comment: '序号'
        isPrimary comment: '是否主项目'
    }
}
