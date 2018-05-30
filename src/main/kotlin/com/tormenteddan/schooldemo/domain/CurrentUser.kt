package com.tormenteddan.schooldemo.domain

import org.springframework.security.core.authority.AuthorityUtils


class CurrentUser(private val user: User) :
        org.springframework.security.core.userdetails.User(
                user.userId,
                user.passwordHash,
                AuthorityUtils.createAuthorityList(user.role.toString())) {
    val userId
        get() = user.userId
    val role
        get() = user.role
}