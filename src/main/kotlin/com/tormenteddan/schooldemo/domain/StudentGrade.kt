package com.tormenteddan.schooldemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Year

@Document
data class StudentGrade(val student: Student,
                        val subject: Subject,
                        val partial: Partial,
                        val grade: Int = 0,
                        val year: Int = Year.now().value) {
    @Id
    val id = "${student.id}:${subject.name}@${partial.name}$year"
}