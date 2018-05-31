package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.CurrentUser
import com.tormenteddan.schooldemo.domain.Role
import org.springframework.stereotype.Service

@Service
class CurrentUserService(val teacherService: TeacherService) {
    fun canAccessGroup(currentUser: CurrentUser?, groupId: String?): Boolean {
        return currentUser != null && (currentUser.role === Role.ADMIN ||
                (groupId != null && currentUser.userId == teacherService.findByGroupId(groupId).id))
    }
}