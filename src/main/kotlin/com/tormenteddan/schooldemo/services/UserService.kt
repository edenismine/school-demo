package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.AbstractUser
import com.tormenteddan.schooldemo.domain.User
import com.tormenteddan.schooldemo.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(val userRepository: UserRepository) {
    fun getAllUsers(): Collection<User> =
            userRepository.findAll(Sort.by("userId", "role"))

    fun getUserById(userId: String): Optional<User> =
            userRepository.findById(userId)

    fun createUser(abstractUser: AbstractUser): User {
        val user = User.build(abstractUser)
        return userRepository.save(user)
    }
}