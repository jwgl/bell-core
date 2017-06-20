package cn.edu.bnuz.bell.operation

import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class ScheduleService {
    List getTeacherSchedules(Integer termId, String teacherId) {
        TaskSchedule.executeQuery '''
select new map(
  schedule.id as id,
  task.id as taskId,
  courseClass.id as courseClassId,
  courseClass.name as courseClassName,
  teacher.id as teacherId,
  teacher.name as teacherName,
  schedule.startWeek as startWeek,
  schedule.endWeek as endWeek,
  schedule.oddEven as oddEven,
  schedule.dayOfWeek as dayOfWeek,
  schedule.startSection as startSection,
  schedule.totalSection as totalSection,
  course.name as course,
  courseItem.name as courseItem,
  place.name as place
)
from CourseClass courseClass
join courseClass.course course
join courseClass.tasks task
join task.schedules schedule
join schedule.teacher teacher
left join task.courseItem courseItem
left join schedule.place place
where teacher.id = :teacherId
  and courseClass.term.id = :termId
''', [teacherId: teacherId, termId: termId]
    }

    List getStudentSchedules(Integer termId, String studentId) {
        TaskSchedule.executeQuery '''
select new map(
  schedule.id as id,
  task.id as taskId,
  courseClass.id as courseClassId,
  courseClass.name as courseClassName,
  teacher.id as teacherId,
  teacher.name as teacherName,
  schedule.startWeek as startWeek,
  schedule.endWeek as endWeek,
  schedule.oddEven as oddEven,
  schedule.dayOfWeek as dayOfWeek,
  schedule.startSection as startSection,
  schedule.totalSection as totalSection,
  course.name as course,
  courseItem.name as courseItem,
  place.name as place
)
from CourseClass courseClass
join courseClass.course course
join courseClass.tasks task
join task.schedules schedule
join task.students taskStudent
join schedule.teacher teacher
left join task.courseItem courseItem
left join schedule.place place
where taskStudent.student.id = :studentId
  and courseClass.term.id = :termId
''', [studentId: studentId, termId: termId]
    }
}
