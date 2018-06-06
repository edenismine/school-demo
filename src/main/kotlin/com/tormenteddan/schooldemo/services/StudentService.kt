package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Student
import com.tormenteddan.schooldemo.domain.Teacher
import com.tormenteddan.schooldemo.domain.UserForm
import com.tormenteddan.schooldemo.repository.StudentRepository
import com.tormenteddan.schooldemo.repository.TeacherRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class StudentService(val repo: StudentRepository,
                     val groupService: GroupService) {
    fun findAll(): List<Student> = repo.findAll()
    fun findById(id: String) = repo.findById(id)
    fun findByGroupId(id: String): List<Student> = repo.findByGroupId(id)
    fun newStudent(form: UserForm): Student {
        val (name, last, groupId) = form
        if (name.isEmpty() || last.isEmpty()) {
            throw IllegalArgumentException("Invalid user data name and last name are empty, should be filled.")
        }
        val group = groupService.findById(groupId).orElse(groupService.noGroup)
        return Student(name, last, group)
    }
    fun save(student: Student): Student = repo.save(student)
}