package cn.edu.bnuz.bell.planning

import cn.edu.bnuz.bell.master.Major


/**
 * 教学计划
 * @author yanglin
 *
 */
class Program {
    /**
     * 教学计划号
     * <pre>
     * 按专业培养方案产生的教学计划
     * 201401010
     * --------=
     *   |     |
     *   |     `-- 类别(1) - 0:主修;1:辅修
     *   `-------- 专业(8) - {@link Major#id}
     * </pre>
     */
    Integer id

    /**
     * 所属专业
     */
    Major major

    /**
     * 类别-0:主修;1:辅修;2:专升本（插班）;
     */
    Integer type

    /**
     * 总学分
     */
    Integer credit

    static hasMany = [
        programProperties: ProgramProperty
    ]

    static mapping = {
        comment '教学计划'
        table   schema: 'ea'
        id      generator: 'assigned', comment: '教学计划ID'
        major   comment: '所属专业'
        type    defalutValue: "0", comment: '类别-0:主修;1:辅修;2:专升本（插班）'
        credit  comment: '总学分'
    }
}
