package cn.edu.bnuz.bell.security

/**
 * 用户类型
 */
enum UserType {
    TEACHER(1, "Teacher"),
    STUDENT(2, "Student"),
    EXTERNAL(9, "External")

    final Integer id
    final String name

    private UserType(int id, String name) {
        this.id = id
        this.name = name
    }

    String toString() {
        name
    }
}