package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String>