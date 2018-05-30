package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Teacher
import org.springframework.data.mongodb.repository.MongoRepository

interface TeacherRepository : MongoRepository<Teacher, String> {
    fun findByGroupId(id: String): Teacher
    fun findByFirstName(firstName: String): List<Teacher>
    fun findByLastName(lastName: String): List<Teacher>
}