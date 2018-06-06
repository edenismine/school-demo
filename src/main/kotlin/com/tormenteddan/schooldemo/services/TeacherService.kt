package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Teacher
import com.tormenteddan.schooldemo.domain.UserForm
import com.tormenteddan.schooldemo.repository.GroupRepository
import com.tormenteddan.schooldemo.repository.TeacherRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class TeacherService(private val teacherRepo: TeacherRepository,
                     private val groupService: GroupService) {
    fun findAll(): List<Teacher> = teacherRepo.findAll()
    fun findById(id: String) = teacherRepo.findById(id)
    fun findByGroupId(groupId: String): Teacher = teacherRepo.findByGroupId(groupId).orElse(noTeacher)
    fun newTeacher(form: UserForm): Teacher {
        val (name, last, groupId) = form
        if (name.isEmpty() || last.isEmpty()) {
            return noTeacher
        }
        val group = groupService.findById(groupId).orElse(groupService.noGroup)
        return Teacher(name, last, group)
    }

    fun save(teacher: Teacher): Teacher {
        return if (teacherRepo.findOne(Example.of(teacher)).isPresent) {
            teacher // return
        } else {
            val oldTeacher = findByGroupId(teacher.group.id)
            if (oldTeacher != noTeacher && oldTeacher != teacher) {
                teacherRepo.save(oldTeacher.copy(group = groupService.noGroup))
                teacherRepo.save(teacher) // return
            } else oldTeacher // return
        }
    }

    val noTeacher: Teacher = Teacher("Unassigned", "Staff", groupService.noGroup, "00000000")
}