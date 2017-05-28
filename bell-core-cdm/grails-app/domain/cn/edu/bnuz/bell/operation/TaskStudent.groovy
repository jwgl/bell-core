package cn.edu.bnuz.bell.operation

import cn.edu.bnuz.bell.organization.Student
import org.codehaus.groovy.util.HashCodeHelper

/**
 * 选课学生
 * @author Yang Lin
 */
class TaskStudent implements Serializable {
    private static final long serialVersionUID = 1

    /**
     * 学生
     */
    Student student

    /**
     * 选课时间
     */
    Date dateCreated

    /**
     * 选课方式-0:排课;1:自选;2:跨专业选课
     */
    Integer registerType

    /**
     * 重修类型-0:正常;1:重修;2:及格重修;3:直接考试;9:重考
     */
    Integer repeatType

    /**
     * 考试标记-0:正常;1:取消资格;2:缓考
     */
    Integer examFlag

    static belongsTo = [task: Task]

    static mapping = {
        comment      '学生选课'
        table        schema: 'ea'
        id           composite: ['task', 'student'], comment: '选课ID'
        task         comment: '教学任务'
        student      comment: '学生'
        dateCreated  comment: '选课时间'
        registerType defaultValue: "0", comment: '选课方式-0:排课;1:自选;2:跨专业选课'
        repeatType   defaultValue: "0", comment: '重修类型-0:正常;1:重修;2:及格重修;3:直接考试;9:重考'
        examFlag     defaultValue: "0", comment: '考试标记-0:正常;1:取消资格;2:缓考考'
    }

    boolean equals(other) {
        if (!(other instanceof TaskStudent)) {
            return false
        }

        other.task?.id == task?.id && other.student?.id == student?.id
    }

    int hashCode() {
        int hash = HashCodeHelper.initHash()
        hash = HashCodeHelper.updateHash(hash, task.id)
        hash = HashCodeHelper.updateHash(hash, student.id)
        hash
    }
}
