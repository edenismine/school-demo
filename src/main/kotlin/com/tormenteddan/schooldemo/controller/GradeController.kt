package com.tormenteddan.schooldemo.controller

import com.tormenteddan.schooldemo.domain.Grade
import com.tormenteddan.schooldemo.services.GradeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GradeController(val gradeService: GradeService) {
    @GetMapping("/api/grades")
    fun findAll(): List<Grade> = gradeService.findAll()

    @GetMapping("/api/grades/{gradeId}")
    fun findById(@PathVariable gradeId: Int) = gradeService.findById(gradeId)
}