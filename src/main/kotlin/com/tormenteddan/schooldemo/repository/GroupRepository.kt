package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Group
import org.springframework.data.mongodb.repository.MongoRepository

interface GroupRepository : MongoRepository<Group, String> {
    fun findByGradeId(id: Int): List<Group>
}