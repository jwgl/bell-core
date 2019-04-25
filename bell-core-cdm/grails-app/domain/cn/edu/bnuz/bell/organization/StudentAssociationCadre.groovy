package cn.edu.bnuz.bell.organization

/**
 * 学生社团干部
 * @author Yang Lin
 */
class StudentAssociationCadre implements Serializable {
    /**
     * 学生
     */
    Student student

    /**
     * 职务
     */
    String post

    static belongsTo = [studentAssociation: StudentAssociation]

    static mapping = {
        comment            '学生社团干部'
        table              schema: 'ea'
        id                 composite: ['studentAssociation', 'student']
        studentAssociation comment: '学生社团'
        student            comment: '学生'
        post               type: 'text', comment: '职务'
    }

    boolean equals(other) {
        if (!(other instanceof AdminClassCadre)) {
            return false
        }

        other.adminClass?.id == studentAssociation?.id && other.student?.id == student?.id
    }

    int hashCode() {
        Objects.hash(studentAssociation.id, student.id)
    }
}
