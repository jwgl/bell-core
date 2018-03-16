package cn.edu.bnuz.bell.organization

import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class TeacherService {
    def getTeachersByDepartment(String departmentId) {
        Teacher.executeQuery '''
select new Map(
  id as id,
  name as name
)
from Teacher
where atSchool = true
and department.id = :departmentId
order by id
''', [departmentId: departmentId]
    }

    def find(String query) {
        Teacher.executeQuery '''
select new Map(
  t.id as id,
  t.name as name,
  d.name as department
)
from Teacher t
join t.department d
where t.atSchool = true
and (t.id like :query or t.name like :query or d.name like :query)
''', [query: "%${query}%"]
    }
}
