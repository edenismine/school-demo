package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.CurrentUser
import com.tormenteddan.schooldemo.domain.Role
import org.springframework.stereotype.Service
import kotlin.math.absoluteValue


@Service
class CurrentUserService {
    fun canAccessUser(currentUser: CurrentUser?, userId: Long?): Boolean {
        return currentUser != null && (currentUser.role === Role.ADMIN ||
                currentUser.userId == (userId?.toInt() ?: 0).absoluteValue.toString().padStart(8, '0'))
    }
}