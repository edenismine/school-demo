package com.tormenteddan.schooldemo.repository

import com.tormenteddan.schooldemo.domain.Partial
import com.tormenteddan.schooldemo.domain.StudentGrade
import com.tormenteddan.schooldemo.domain.Subject
import org.springframework.data.mongodb.repository.MongoRepository

interface StudentGradeRepository : MongoRepository<StudentGrade, String> {
    fun findByStudentId(studentId: String): List<StudentGrade>
    fun findByStudentIdAndYear(studentId: String, year: Int): List<StudentGrade>
    fun findByStudentIdAndPartialAndYear(studentId: String, partial: Partial, year: Int) : List<StudentGrade>
    fun findByStudentIdAndPartialAndYearAndSubject(studentId: String, partial: Partial, year: Int, subject: Subject): StudentGrade
}