package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Teacher
import com.tormenteddan.schooldemo.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class TeacherService(val repo: TeacherRepository) {
    fun findAll(): List<Teacher> = repo.findAll()
    fun findById(id: String) = repo.findById(id)
    fun findByGroupId(id: String): Teacher = repo.findByGroupId(id)
    fun findByFirstName(firstName: String): List<Teacher> = repo.findByFirstName(firstName)
    fun findByLastName(lastName: String): List<Teacher> = repo.findByLastName(lastName)
}