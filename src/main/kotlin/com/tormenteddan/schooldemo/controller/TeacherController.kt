package com.tormenteddan.schooldemo.controller

import com.tormenteddan.schooldemo.services.GroupService
import com.tormenteddan.schooldemo.services.TeacherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TeacherController(val service: TeacherService) {
    @GetMapping("/api/grades/{gradeId}/groups/{groupId}/teacher")
    fun findByGroupId(@PathVariable groupId: String) = service.findByGroupId(groupId)

    @GetMapping("/api/teachers/{teacherId}")
    fun findById(@PathVariable teacherId: String) = service.findById(teacherId)

    @GetMapping("/api/teachers")
    fun findAll() = service.findAll()

}