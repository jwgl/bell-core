package cn.edu.bnuz.bell.organization

import java.time.LocalDate

import cn.edu.bnuz.bell.entrance.Admission
import cn.edu.bnuz.bell.planning.Direction
import cn.edu.bnuz.bell.master.Major

/**
 * 学生
 * @author Yang Lin
 */
class Student {
    /**
     * 学号
     */
    String id

    /**
     * 姓名
     */
    String name

    /**
     * 姓名拼音
     */
    String pinyinName

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
    Integer nationality

    /**
     * 政治面貌
     */
    Integer politicalStatus

    /**
     * 入学日期
     */
    LocalDate dateEnrolled

    /**
     * 毕业日期
     */
    LocalDate dateGraduated

    /**
     * 是否有学籍
     */
    Boolean isEnrolled

    /**
     * 是否在校
     */
    Boolean atSchool

    /**
     * 是否注册
     */
    Boolean isRegistered

    /**
     * 乘车区间
     */
    String trainRange

    /**
     * 学生类别：
     * 计划内
     * 计划外
     * 留学生
     * 网络生
     * 专升本插班生
     * 计划内提高班
     * 香港免试生
     * 港澳台生
     * 交换生
     * 旁听生
     */
    Integer category

    /**
     * 异动类别
     */
    Integer changeType

    /**
     * 主修外语语种:英en、法fr、德de、日ja、俄ru
     */
    String foreignLanguage

    /**
     * 外语等级
     */
    Integer foreignLanguageLevel

    /**
     * 学院
     */
    Department department

    /**
     * 行政班
     */
    AdminClass adminClass

    /**
     * 专业
     */
    Major major

    /**
     * 专业方向
     */
    Direction direction

    /**
     * 录取信息
     */
    Admission admission

    static mapping = {
        comment              '学生'
        table                schema: 'ea'
        id                   generator: 'assigned', length: 10, comment: '学号'
        name                 length: 50, comment: '姓名'
        pinyinName           length: 50,    comment: '姓名拼音'
        sex                  length: 1, comment: '性别'
        birthday             comment: '出生日期'
        politicalStatus      comment: '政治面貌'
        nationality          comment: '民族'
        dateEnrolled         comment: '入学日期'
        dateGraduated        comment: '毕业日期'
        isEnrolled           defaultValue: "true", comment: '是否有学籍'
        atSchool             defaultValue: "true", comment: '是否在校'
        isRegistered         defaultValue: "true", comment: '是否注册'
        trainRange           length: 20, comment: '乘车区间'
        category             comment: '学生类别'
        foreignLanguage      length: 2, comment: '主修外语种类'
        foreignLanguageLevel defaultValue: "0", comment: '外语等级'
        changeType           comment: '异动类别'
        department           comment: '学院'
        adminClass           comment: '行政班'
        major                comment: '专业'
        direction            comment: '专业方向'
        admission            comment: '录取信息'
    }

    static constraints = {
        pinyinName           nullable: true
        birthday             nullable: true
        politicalStatus      nullable: true
        nationality          nullable: true
        dateGraduated        nullable: true
        trainRange           nullable: true
        foreignLanguageLevel nullable: true
        changeType           nullable: true
        adminClass           nullable: true
        direction            nullable: true
        admission            nullable: true
    }
}
