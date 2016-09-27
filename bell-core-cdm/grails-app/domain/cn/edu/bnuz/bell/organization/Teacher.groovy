package cn.edu.bnuz.bell.organization

import java.time.LocalDate

/**
 * 教师
 * @author Yang Lin
 */
class Teacher {
    /**
     * 教师职工号
     */
    String id

    /**
     * 姓名
     */
    String name

    /**
     * 性别
     */
    String sex

    /**
     * 出生日期
     */
    LocalDate birthday

    /**
     * 政治面貌
     */
    String politicalStatus

    /**
     * 民族
     */
    String nationality

    /**
     * 职称
     */
    String academicTitle


    /**
     * 教师级别
     */
    String academicLevel

    /**
     * 学位
     */
    String academicDegree

    /**
     * 学历
     */
    String educationalBackground

    /**
     * 毕业学校
     */
    String graduateSchool

    /**
     * 毕业专业
     */
    String graduateMajor

    /**
     * 毕业时间
     */
    LocalDate dateGraduated

    /**
     * 简介
     */
    String resume

    /**
     * 是否具有教师资格
     */
    Boolean hasQualification

    /**
     * 在职类别
     */
    String postType

    /**
     * 是否实验人员
     */
    Boolean isLabTechnician

    /**
     * 是否外聘
     */
    Boolean isExternal

    /**
     * 是否在岗
     */
    Boolean atSchool

    /**
     * 是否具有指导学生毕业设计资格
     */
    Boolean canGuidanceGraduate

    /**
     * 所属部门
     */
    Department department

    static belongsTo = [department: Department]

    static mapping = {
        comment               '教师'
        table                 schema: 'ea'
        id                    generator: 'assigned', length: 5, comment: '教师职工号'
        name                  length: 50, comment: '姓名'
        sex                   length: 1, comment: '性别'
        birthday              comment: '出生日期'
        politicalStatus       length: 50, comment: '政治面貌'
        nationality           length: 20, comment: '民族'
        academicTitle         length: 20, comment: '职称'
        academicLevel         length: 10, comment: '职称等级'
        academicDegree        length: 20, comment: '学位'
        educationalBackground length: 20, comment: '学历'
        graduateSchool        length: 50, comment: '毕业学校'
        graduateMajor         length: 50, comment: '毕业专业'
        dateGraduated         comment: '毕业时间'
        resume                length: 2000, comment: '简历'
        postType              length: 10, comment: '岗位类别'
        hasQualification      defaultValue: "false", comment: '是否具有教师资格'
        isLabTechnician       defaultValue: "false", comment: '是否为实验员'
        isExternal            defaultValue: "false", comment: '是否为外聘教师'
        atSchool              defaultValue: "false", comment: '是否在校'
        canGuidanceGraduate   defaultValue: "false", comment: '是否可以指导毕业设计'
        department            comment: '所在部门'
    }

    static constraints = {
        sex                   nullable: true, maxSize: 1
        birthday              nullable: true
        politicalStatus       nullable: true, maxSize: 50
        nationality           nullable: true, maxSize: 20
        academicTitle         nullable: true, maxSize: 20
        academicLevel         nullable: true, maxSize: 10
        academicDegree        nullable: true, maxSize: 20
        educationalBackground nullable: true, maxSize: 20
        graduateSchool        nullable: true, maxSize: 50
        graduateMajor         nullable: true, maxSize: 50
        dateGraduated         nullable: true
        resume                nullable: true, maxSize: 2000
        postType              nullable: true, maxSize: 10
    }

    /**
     * 是否为班主任
     */
    boolean isHeadTeacher() {
        return AdminClass.countByHeadTeacher(this)    > 0
    }

    /**
     * 是否为辅导员
     */
    boolean isCounselor() {
        return AdminClass.countByCounselor(this) > 0
    }

    /**
     * 所在部门是否有学生
     */
    boolean isCollegeTeacher() {
        return department.hasStudents
    }
}
