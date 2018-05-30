package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.CurrentUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CurrentUserDetailsService(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return CurrentUser(userService.getUserById(username ?: "NULL")
                .orElseThrow {
                    UsernameNotFoundException("User with user ID $username was not found.")
                })
    }

}