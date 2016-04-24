package cn.edu.bnuz.bell.master

/**
 * 课程性质
 * @author Yang Lin
 */
class Property {
    /**
     * 课程性质ID
     */
    Integer id

    /**
     * 性质名称
     */
    String name

    /**
     * 简称
     */
    String shortName

    /**
     * 是否必修
     */
    Boolean isCompulsory

    /**
     * 是否主干课
     */
    Boolean isPrimary

    /**
     * 是否含专业方向
     */
    Boolean hasDirections

    /**
     * 是否有效
     */
    Boolean enabled

    static mapping = {
        comment       '课程性质'
        table         schema: 'ea'
        id            generator: 'assigned', comment: '课程性质ID'
        name          length: 50, comment: '名称'
        shortName     length: 20, comment: '简称'
        isCompulsory  defaultValue: "false", comment: '是否为必修课'
        isPrimary     defaultValue: "false", comment: '是否为必修课'
        enabled       defaultValue: "true", comment: '是否有效'
        hasDirections defaultValue: "false", comment: '是否含专业方向'
    }
}
