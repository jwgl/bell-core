package cn.edu.bnuz.bell.planning

import cn.edu.bnuz.bell.master.Discipline
import cn.edu.bnuz.bell.master.Property

/**
 * 课程性质限制
 * @author Yang Lin
 */
class PropertyLimit {
    /**
     * 课程性质
     */
    Property property

    /**
     * 学年
     */
    Integer year

    /**
     * 学科。
     * 如果为空，表示对所有学科的限制；
     * 如果不为空，表示对特定学科的限制。
     */
    Discipline discipline

    /**
     * 学分上限
     */
    Integer maxCredit

    /**
     * 学分下限
     */
    Integer minCredit


    static mapping = {
        comment    '课程性质限制'
        table      schema: 'ea'
        id         comment: '课程性质限制ID'
        property   comment: '课程性质'
        year       comment: '学年'
        discipline comment: '学科'
        maxCredit  comment: '学分上限'
        minCredit  comment: '学分下限'
    }

    static constraints = {
        discipline nullable: true
        property   unique: ['year', 'discipline']
    }
}
