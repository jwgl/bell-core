package cn.edu.bnuz.bell.organization

/**
 * 学生社团
 * @author Yang Lin
 */
class StudentAssociation {
    /**
     * 学生社团ID
     */
    String id

    /**
     * 名称
     */
    String name

    static belongsTo = [department: Department]

    static hasMany = [
            cadres: StudentAssociationCadre
    ]

    static mapping = {
        comment     '学生社团'
        table       schema: 'ea'
        id          generator: 'assigned', type: 'text', comment: '学生社团ID'
        name        type: 'text', comment: '名称'
        department  comment: '所属单位'
    }
}
