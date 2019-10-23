package cn.edu.bnuz.bell.master

/**
 * 普通高等学校本科专业目录（2012年）
 * @author Yang Lin
 */
class Field {
    /**
     * 统招专业ID
     * 2012080902
     * ----======
     *   | |
     *   | `-- 专业代码
     *   `-----发布年份
     */
    Integer id

    /**
     * 代码
     */
    String code

    /**
     * 名称
     */
    String name

    /**
     * 标记
     * 1998版:
     * *-目录内需一般控制设置的专业
     * △-内需从严控制设置的专业
     * Y-引导性专业
     * W-目录外专业
     * S-少数高校试点的目录外专业
     * 
     * 2012版:
     * T-特设专业
     * K-国家控制布点专业
     */
    String flag

    static belongsTo = [
        fieldClass: FieldClass    
    ]

    static hasMany = [
        allowDegrees: FieldAllowDegree  
    ]

    static mapping = {
        comment    '统招专业'
        table      schema: 'ea'
        id         generator: 'assigned', comment: '统招专业ID'
        code       length: 8, comment: '代码'
        name       length: 30, comment: '名称'
        flag       length: 2, comment: '标记'
        fieldClass length: 20, comment: '专业类'
    }

    static constraints = {
        flag       nullable: true, maxSize: 2
    }
}
