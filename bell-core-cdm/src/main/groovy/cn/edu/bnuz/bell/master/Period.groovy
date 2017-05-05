package cn.edu.bnuz.bell.master

import grails.gorm.dirty.checking.DirtyCheck

/**
 * 课时
 * @author Yang Lin
 */
@DirtyCheck
class Period {
    /**
     * 周理论课时(HPW - Hours Per Week)
     */
    BigDecimal theory

    /**
     * 周实验课时(HPW - Hours Per Week)
     */
    BigDecimal experiment

    /**
     * 周数(Weeks)
     * 如果为实践课，表示实践周数；如果为理论(含实验)课，表示理论上课周数
     */
    Integer weeks

    static mapping = {
        theory     precision: 3, scale: 1, defaultValue: "0", comment: '理论周课时'
        experiment precision: 3, scale: 1, defaultValue: "0", comment: '实验周课时'
        weeks      defaultValue: "0", comment: '周数'
    }
}
