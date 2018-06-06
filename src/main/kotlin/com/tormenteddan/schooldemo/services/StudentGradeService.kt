package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.GradingForm
import com.tormenteddan.schooldemo.domain.Partial
import com.tormenteddan.schooldemo.domain.StudentGrade
import com.tormenteddan.schooldemo.domain.Subject
import com.tormenteddan.schooldemo.repository.StudentGradeRepository
import org.springframework.stereotype.Service
import java.time.Year

@Service
class StudentGradeService(val repo: StudentGradeRepository,
                          val partialService: PartialService,
                          val studentService: StudentService) {
    fun submitGrades(form: GradingForm) : List<StudentGrade>{
        val partial = partialService.findPartial(form.partial)
        val student = studentService.findById(form.student).get()
        if (form.all { it.second in 1..100 }){
            repo.deleteAll(repo.findByStudentIdAndPartialAndYear(
                    form.student, partial, form.year))
        }
        return form.map { (subject, grade)->
            saveGrade(StudentGrade(student, subject, partial, grade, form.year))
        }
    }


    fun findAll(): List<StudentGrade> = repo.findAll()

    fun findByStudentId(studentId: String): List<StudentGrade> =
            repo.findByStudentId(studentId)

    fun findByStudentIdAndYear(studentId: String, year: Int): List<StudentGrade> =
            repo.findByStudentIdAndYear(studentId, year)

    fun findByStudentIdAndPartialAndYear(studentId: String, partial: Partial, year: Int = Year.now().value) : List<StudentGrade> =
            repo.findByStudentIdAndPartialAndYear(studentId, partial, year)

    fun findByStudentIdAndPartialAndYearAndSubject(studentId: String, partial: Partial, year: Int = Year.now().value,
                                                   subject: Subject) =
            repo.findByStudentIdAndPartialAndYearAndSubject(studentId, partial, year, subject)

    fun partialAverage(studentId: String, partial: Partial, year: Int = Year.now().value): Int {
        val grades = repo.findByStudentIdAndPartialAndYear(studentId, partial, year)
        return try {
            grades.sumBy { it.grade } / grades.size
        } catch (e: ArithmeticException) {
            0
        }
    }

    fun yearAverage(studentId: String, year: Int = Year.now().value): Int {
        val grades = repo.findByStudentIdAndYear(studentId, year)
        return try {
            grades.sumBy { it.grade } / grades.size
        } catch (e: ArithmeticException) {
            0
        }
    }

    fun accumulatedAverage(studentId: String): Int {
        val grades = repo.findByStudentId(studentId)
        return try {
            grades.sumBy { it.grade } / grades.size
        } catch (e: ArithmeticException){
            0
        }
    }

    fun saveGrade(grade: StudentGrade) : StudentGrade{
        return repo.save(grade)
    }
}