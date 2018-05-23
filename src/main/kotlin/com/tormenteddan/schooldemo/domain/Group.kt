package com.tormenteddan.schooldemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "groups")
data class Group(@Id val id: String = "", val grade: Grade? = null)