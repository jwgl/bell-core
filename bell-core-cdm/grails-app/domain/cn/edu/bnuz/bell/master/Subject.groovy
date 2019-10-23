package cn.edu.bnuz.bell.master

import cn.edu.bnuz.bell.organization.Department

/**
 * 内部专业（年级专业模板）
 * @author Yang Lin
 */
class Subject {
    /**
     * 内部专业ID
     * <pre>
     * 0101
     * --=-
     * | ||
     * | | `-- 主专业
     * | `---- 派生专业
     * `------ 学院代码
     * </pre>
     */
    String id

    /**
     * 名称
     */
    String name

    /**
     * 英文名称
     */
    String englishName

    /**
     * 简称
     */
    String shortName

    /**
     * 层次-1:本科;2:硕士;3:博士;9:其它
     */
    Integer educationLevel

    /**
     * 学制
     */
    Integer lengthOfSchooling

    /**
     * 停止招生
     */
    Boolean stopEnroll

    /**
     * 是否中外合作办学项目（4+0）
     * Sino-Foreign Joint Program
     */
    Boolean isJointProgram

    /**
     * 是否中外联合培养双学位（2+2）
     */
    Boolean isDualDegree

    /**
     * 是否专升本
     */
    Boolean isTopUp

    /**
     * 统招专业
     */
    Field field

    /**
     * 授予学位
     */
    Discipline degree

    /**
     * 所属学院
     */
    Department department

    static belongsTo = [department : Department]

    static mapping = {
        comment           '内部专业'
        table             schema: 'ea'
        id                generator: 'assigned', length: 4, comment: '内部专业ID'
        name              length: 50, comment: '名称'
        englishName       length: 100, comment: '英文名称'
        shortName         length: 20, comment: '简称'
        educationLevel    defaultValue: "1", comment: '层次'
        lengthOfSchooling defaultValue: "4", comment: '学制'
        stopEnroll        defaultValue: "false", comment: '是否停止招生'
        isJointProgram    defaultValue: "false", comment: '是否中外合作办学项目（4+0）'
        isDualDegree      defaultValue: "false", comment: '是否中外联合培养双学位（2+2）'
        isTopUp           defaultValue: "false", comment: '是否专升本'
        field             comment: '统招专业'
        degree            comment: '授予学位'
        department        comment: '所属学院'
    }

    static constraints = {
        englishName       nullable: true, maxSize: 100
        field             nullable: true
        degree            nullable: true
    }
}
