package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Partial
import com.tormenteddan.schooldemo.domain.StudentGrade
import com.tormenteddan.schooldemo.repository.StudentGradeRepository
import org.springframework.stereotype.Service

@Service
class StudentGradeService(val repo: StudentGradeRepository) {
    fun findByStudentId(studentId: String): List<StudentGrade> =
            repo.findByStudentId(studentId)

    fun findByStudentIdAndPartial(studentId: String, partial: Partial): List<StudentGrade> =
            repo.findByStudentIdAndPartial(studentId, partial)

    fun findByStudentIdAndYear(studentId: String, year: Int): List<StudentGrade> =
            repo.findByStudentIdAndYear(studentId, year)

    fun periodAverage(studentId: String, partial: Partial): Int {
        val grades = repo.findByStudentIdAndPartial(studentId, partial)
        return grades.sumBy { it.grade } / grades.size
    }

    fun yearAverage(studentId: String, year: Int): Int {
        val grades = repo.findByStudentIdAndYear(studentId, year)
        return grades.sumBy { it.grade } / grades.size
    }

}