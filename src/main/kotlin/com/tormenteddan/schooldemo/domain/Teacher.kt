package com.tormenteddan.schooldemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "teachers")
data class Teacher(val firstName: String = "John",
                   val lastName: String = "Smith",
                   val group: Group,
                   @Id val id: String = UUID.randomUUID().toString())