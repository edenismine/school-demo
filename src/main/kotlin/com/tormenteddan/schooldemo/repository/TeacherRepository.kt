package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Teacher
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TeacherRepository : MongoRepository<Teacher, String> {
    fun findByGroupId(id: String): Optional<Teacher>
}