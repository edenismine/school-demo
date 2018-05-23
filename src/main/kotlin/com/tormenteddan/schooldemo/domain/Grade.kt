package com.tormenteddan.schooldemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "grades")
data class Grade(@Id val id: Int = 0, val school: String = "Elementary School")