package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Grade
import com.tormenteddan.schooldemo.repository.GradeRepository
import org.springframework.stereotype.Service

@Service
class GradeService(val repo: GradeRepository) {
    fun findAll(): List<Grade> = repo.findAll()
    fun findById(id: Int) = repo.findById(id)
}
