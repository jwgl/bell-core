package cn.edu.bnuz.bell.organization

import cn.edu.bnuz.bell.master.Major

/**
 * 部门（行政/学院）
 * @author yanglin
 *
 */
class Department {
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
     * 是否有学生
     */
    Boolean hasStudents

    /**
     * 是否为教学单位
     */
    Boolean isTeaching

    /**
     * 是否有效
     */
    Boolean enabled

    static hasMany = [
        teachers:     Teacher,
        majors:       Major,
        adminClasses: AdminClass
    ]

    static mapping = {
        cache       true
        comment     '部门'
        table       schema: 'ea'
        id          generator: 'assigned', length: 2, comment: '部门ID'
        name        length:50, comment: '名称'
        englishName length:50, comment: '英文名称'
        shortName   length:20, comment: '简称'
        hasStudents defaultValue: "true", comment: '是否有学生'
        isTeaching  defaultValue: "true", comment: '是否为教学单位'
        enabled     defaultValue: "true", comment: '是否有效'
    }

    static constraints = {
        englishName nullable: true, maxSize: 50
        shortName   nullable: true, maxSize: 20
    }
}
