package cn.edu.bnuz.bell.master

class Period {
    /**
     * 周理论课时(HPW - Hours Per Week)
     */
    Integer theory

    /**
     * 周实验课时(HPW - Hours Per Week)
     */
    Integer experiment

    /**
     * 周数(Weeks)
     * 如果为实践课，表示实践周数；如果为理论(含实验)课，表示理论上课周数
     */
    Integer weeks

    static mapping = {
        theory     defaultValue: "0", comment: '理论周课时'
        experiment defaultValue: "0", comment: '实验周课时'
        weeks      defaultValue: "0", comment: '周数'
    }
}
