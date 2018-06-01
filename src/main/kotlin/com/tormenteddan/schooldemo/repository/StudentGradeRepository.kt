package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Partial
import com.tormenteddan.schooldemo.domain.StudentGrade
import org.springframework.data.mongodb.repository.MongoRepository

interface StudentGradeRepository : MongoRepository<StudentGrade, String> {
    fun findByStudentId(studentId: String): List<StudentGrade>
    fun findByStudentIdAndPartial(studentId: String, partial: Partial): List<StudentGrade>
    fun findByStudentIdAndYear(studentId: String, year: Int): List<StudentGrade>
}