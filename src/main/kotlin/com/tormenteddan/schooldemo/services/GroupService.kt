package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Group
import com.tormenteddan.schooldemo.repository.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupService(private val repo: GroupRepository) {
    fun findAll(): List<Group> = repo.findAll()
    fun findById(id: String) = repo.findById(id)
    fun findByGradeId(id: Int): List<Group> = repo.findByGradeId(id)
    val noGroup : Group = Group("00")
}