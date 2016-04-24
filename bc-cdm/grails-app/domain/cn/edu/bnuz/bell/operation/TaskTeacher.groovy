package cn.edu.bnuz.bell.operation

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.bell.organization.Teacher

/**
 * 安排-教师
 * @author yanglin
 *
 */
class TaskTeacher implements Serializable {
    private static final long serialVersionUID = 1

    /**
     * 教师
     */
    Teacher teacher

    static belongsTo = [task: Task]

    static mapping = {
        comment '任务-教师'
        table   schema: 'ea'
        id      composite: ['task', 'teacher'], comment: '安排-教师ID'
        teacher comment: '任课教师'
        task    comment: '教学任务'
    }

    boolean equals(other) {
        if (!(other instanceof TaskTeacher)) {
            return false
        }

        other.task?.id == task?.id && other.teacher?.id == teacher?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (task)
            builder.append(task.id)
        if (teacher)
            builder.append(teacher.id)
        builder.toHashCode()
    }
}
