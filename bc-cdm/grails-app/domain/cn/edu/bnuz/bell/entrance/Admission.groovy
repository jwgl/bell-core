package cn.edu.bnuz.bell.entrance

import java.time.LocalDate

import cn.edu.bnuz.bell.master.Subject

/**
 * 学生录取信息
 * @author Yang Lin
 */
class Admission {
    /**
     * 学号
     */
    String studentId

    /**
     * 校内专业
     */
    Subject subject

    /**
     * 年级
     */
    Integer grade

    /**
     * 姓名
     */
    String name

    /**
     * 曾用名
     */
    String usedName

    /**
     * 性别
     */
    String sex

    /**
     * 出生日期
     */
    LocalDate birthday

    /**
     * 民族
     */
    String nationality

    /**
     * 政治面貌
     */
    String politicalStatus

    /**
     * 毕业中学
     */
    String middleSchool

    /**
     * 考生号
     */
    String candidateNumber

    /**
     * 考生类别：
     * 1:文科;
     * 2:理科;
     * 4:艺术;
     * 8:体育;
     * 16:外语类;
     * 32:插班;
     * 64:计划外
     */
    Integer candidateType

    /**
     * 准考证号
     */
    String examinationNumber

    /**
     * 入学总成绩
     */
    Integer totalScore

    /**
     * 入学英语成绩
     */
    Integer englishScore

    /**
     * 身份证号
     */
    String idNumber

    /**
     * 银行帐号
     */
    String bankNumber

    /**
     * 联系电话
     */
    String phoneNumber

    /**
     * 来源省
     */
    String fromProvince

    /**
     * 来源市
     */
    String fromCity

    /**
     * 家庭所在地
     */
    String homeAddress

    /**
     * 邮政编码
     */
    String postalCode

    /**
     * 户口所在地
     */
    String householdAddress


    static mapping = {
        comment           '录取信息'
        table             schema: 'ea'
        id                generator: 'assigned', comment: '录取信息ID'
        studentId         length: 10, comment: '学号'
        subject           comment: '校内专业'
        grade             comment: '年级'
        name              length: 50, comment: '姓名'
        usedName          length: 50, comment: '曾用名'
        sex               length: 1, comment: '性别'
        birthday          comment: '出生日期'
        politicalStatus   length: 50, comment: '政治面貌'
        nationality       length: 20, comment: '民族'
        phoneNumber       length: 50, comment: '联系电话'
        fromProvince      length: 20, comment: '来源省'
        fromCity          length: 50, comment: '来源市'
        homeAddress       length: 100, comment: '家庭所在地'
        householdAddress  length: 20, comment: '户口所在地'
        postalCode        length: 6, comment: '邮政编码'
        middleSchool      length: 100, comment: '毕业中学'
        candidateNumber   length: 18, comment: '考生号'
        candidateType     comment: '考生类别'
        examinationNumber length: 14, comment: '准考证号'
        totalScore        comment: '入学总成绩'
        englishScore      comment: '入学英语成绩'
        idNumber          length: 20, comment: '身份证号'
        bankNumber        length: 21, comment: '银行账号'
    }

    static constraints = {
        studentId         nullable: true, maxSize: 10
        usedName          nullable: true, maxSize: 50
        birthday          nullable: true
        politicalStatus   nullable: true, maxSize: 50
        nationality       nullable: true, maxSize: 20
        phoneNumber       nullable: true, maxSize: 50
        fromProvince      nullable: true, maxSize: 20
        fromCity          nullable: true, maxSize: 50
        homeAddress       nullable: true, maxSize: 100
        householdAddress  nullable: true, maxSize: 20
        postalCode        nullable: true, maxSize: 6
        middleSchool      nullable: true, maxSize: 100
        candidateNumber   nullable: true, maxSize: 18
        candidateType     nullable: true
        examinationNumber nullable: true, maxSize: 14
        totalScore        nullable: true
        englishScore      nullable: true
        idNumber          nullable: true, maxSize: 20
        bankNumber        nullable: true, maxSize: 21
    }
}
