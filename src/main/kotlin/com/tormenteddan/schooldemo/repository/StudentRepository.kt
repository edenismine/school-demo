package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Student
import com.tormenteddan.schooldemo.domain.Teacher
import org.springframework.data.mongodb.repository.MongoRepository

interface StudentRepository : MongoRepository<Student, String> {
    fun findByGroupId(id: String): List<Student>
    fun findByFirstName(firstName: String): List<Student>
    fun findByLastName(lastName: String): List<Student>
}