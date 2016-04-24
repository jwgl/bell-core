package cn.edu.bnuz.bell.place


/**
 * 教学场地
 * @author Yang Lin
 */
class Place {
    String id
    String name
    String englishName
    String building
    String type
    Integer seat
    Integer testSeat
    Boolean enabled
    Boolean canTest
    Boolean isExternal
    String note

    static hasMany = [
        allowDepartments: PlaceDepartment,
        allowBookingTerms : PlaceBookingTerm,
        allowBookingUserTypes: PlaceBookingUserType
    ]

    static mapping = {
        comment     '教学场地'
        table       schema: 'ea'
        id          generator: 'assigned', length: 6, comment: '场地ID'
        name        length: 50,    comment: '场地名称'
        englishName length: 50,    comment: '英文名称'
        building    length: 50,    comment: '教学楼'
        type        length: 50,    comment: '场地类型'
        seat        comment: '座位数'
        testSeat    comment: '考试座位数'
        enabled     comment: '是否可用'
        canTest     comment: '是否可用于考试'
        isExternal  comment: '是否为校外场地'
        note        length: 200, comment: '备注'
    }

    static constraints = {
        seat        nullable: true
        englishName nullable: true, maxSize: 50
        note        nullable: true, maxSize: 200
    }
}
