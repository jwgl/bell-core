package cn.edu.bnuz.bell.master

import cn.edu.bnuz.bell.organization.Department
import cn.edu.bnuz.bell.planning.Program


/**
 * 年级专业
 * @author Yang Lin
 */
class Major {
    /**
     * 年级专业ID
     * <pre>
     * 20140101
     * ----==-=
     *  |  | ||
     *  |  | | `-- 主专业
     *  |  | `---- 派生专业
     *  |  `------ 学院代码
     *   `-------- 年级
     * </pre>
     */
    Integer id

    /**
     * 年级
     */
    Integer grade

    /**
     * 考生类别
     */
    Integer candidateType

    /**
     * 统招专业
     */
    Field field

    /**
     * 校内专业
     */
    Subject subject

    /**
     * 授予学位
     */
    Discipline degree

    /**
     * 所在学院
     */
    Department department


    static belongsTo = [
        department : Department // 所属学院
    ]

    static hasMany = [
        programs: Program
    ]

    static mapping = {
        comment       '年级专业'
        table         schema: 'ea'
        id            generator: 'assigned', comment: '年级专业ID'
        grade         comment: '年级'
        candidateType comment: '允许考生类别'
        field         comment: '统招专业'
        subject       comment: '校内专业'
        degree        comment: '授予学位'
        department    comment: '所属学院'
    }

    static constraints = {
        field         nullable: true
        degree        nullable: true
    }
}
