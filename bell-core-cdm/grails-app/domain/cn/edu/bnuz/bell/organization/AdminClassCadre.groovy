package cn.edu.bnuz.bell.organization

/**
 * 行政班干部
 * @author Yang Lin
 */
class AdminClassCadre implements Serializable {
    /**
     * 学生
     */
    Student student

    /**
     * 职务
     */
    String post

    static belongsTo = [adminClass: AdminClass]

    static mapping = {
        comment    '行政班干部'
        table      schema: 'ea'
        id         composite: ['adminClass', 'student']
        adminClass comment: '行政班'
        student    comment: '学生'
        post       type: 'text', comment: '职务'
    }

    boolean equals(other) {
        if (!(other instanceof AdminClassCadre)) {
            return false
        }

        other.adminClass?.id == adminClass?.id && other.student?.id == student?.id
    }

    int hashCode() {
        Objects.hash(adminClass.id, student.id)
    }
}
