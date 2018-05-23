package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Grade
import org.springframework.data.mongodb.repository.MongoRepository

interface GradeRepository : MongoRepository<Grade, Int>