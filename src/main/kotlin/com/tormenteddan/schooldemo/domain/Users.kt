package com.tormenteddan.schooldemo.domain

import com.tormenteddan.schooldemo.services.UserIdService
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

sealed class AbstractUser

@Document(collection = "admins")
data class Admin(val firstName: String = "John",
                 val lastName: String = "Smith",
                 @Id val id: String = UserIdService.next()) : AbstractUser()

@Document(collection = "students")
data class Student(val firstName: String = "John",
                   val lastName: String = "Smith",
                   val group: Group,
                   @Id val id: String = UserIdService.next()) : AbstractUser()

@Document(collection = "teachers")
data class Teacher(val firstName: String = "John",
                   val lastName: String = "Smith",
                   val group: Group,
                   @Id val id: String = UserIdService.next()) : AbstractUser()

@Document
data class User(
        val passwordHash: String,
        val role: Role,
        @Id val userId: String = UserIdService.next()) {
    companion object {
        fun build(user: AbstractUser): User {
            // TODO randomize passwords
            return when (user) {
                is Student -> User(BCryptPasswordEncoder().encode(user.lastName), Role.STUDENT, user.id)
                is Admin -> User(BCryptPasswordEncoder().encode(user.lastName), Role.ADMIN, user.id)
                is Teacher -> User(BCryptPasswordEncoder().encode(user.lastName), Role.TEACHER, user.id)
            }
        }
    }
}