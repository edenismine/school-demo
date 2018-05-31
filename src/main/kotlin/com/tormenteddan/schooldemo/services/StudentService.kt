package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Student
import com.tormenteddan.schooldemo.domain.Teacher
import com.tormenteddan.schooldemo.repository.StudentRepository
import com.tormenteddan.schooldemo.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class StudentService(val repo: StudentRepository) {
    fun findAll(): List<Student> = repo.findAll()
    fun findById(id: String) = repo.findById(id)
    fun findByGroupId(id: String): List<Student> = repo.findByGroupId(id)
    fun findByFirstName(firstName: String): List<Student> = repo.findByFirstName(firstName)
    fun findByLastName(lastName: String): List<Student> = repo.findByLastName(lastName)
}