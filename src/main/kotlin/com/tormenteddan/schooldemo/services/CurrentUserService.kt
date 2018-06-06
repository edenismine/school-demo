package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.*
import com.tormenteddan.schooldemo.domain.Role.*
import org.springframework.stereotype.Service

@Service
class CurrentUserService(val teacherService: TeacherService, val studentService: StudentService) {
    fun canAccess(currentUser: CurrentUser?, groupId: String?, studentId : String?): Boolean {
        return if(currentUser != null){
            val isAdmin = currentUser.role == ADMIN
            val user = getUser(currentUser)
            val canAccess : Boolean = when(user){
                is Admin -> true
                is Student -> user.group.id == groupId && user.id == studentId
                is Teacher -> user.group?.id == groupId
                null -> false
            }
            isAdmin || canAccess
        } else false
    }

    private fun getUser(currentUser: CurrentUser?) : AbstractUser? {
        return if(currentUser != null){
            when(currentUser.role){
                TEACHER -> teacherService.findById(currentUser.userId).get()
                STUDENT -> studentService.findById(currentUser.userId).get()
                ADMIN -> null
            }
        } else null
    }
}