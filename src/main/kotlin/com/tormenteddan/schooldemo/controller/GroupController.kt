package com.tormenteddan.schooldemo.controller

import com.tormenteddan.schooldemo.services.GroupService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(val service: GroupService) {
    @GetMapping("/api/grades/{gradeId}/groups")
    fun findByGradeId(@PathVariable gradeId: Int) = service.findByGradeId(gradeId)

    @GetMapping("/api/grades/{gradeId}/groups/{groupId}")
    fun findById(@PathVariable groupId: String) = service.findById(groupId)

}