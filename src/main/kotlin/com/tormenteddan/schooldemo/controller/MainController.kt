package com.tormenteddan.schooldemo.controller

import com.tormenteddan.schooldemo.domain.CurrentUser
import com.tormenteddan.schooldemo.domain.Partial
import com.tormenteddan.schooldemo.domain.Role
import com.tormenteddan.schooldemo.services.StudentGradeService
import com.tormenteddan.schooldemo.services.StudentService
import com.tormenteddan.schooldemo.services.TeacherService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.util.*


@Controller
class MainController(
        val studentService: StudentService,
        val teacherService: TeacherService,
        val studentGradeService: StudentGradeService
) {

    @RequestMapping("/")
    fun root(): String {
        return "redirect:/index"
    }

    @RequestMapping("/index")
    fun index(model: Model): String {
        model.addAttribute("title", "Hypothetical Elementary")
        return "index"
    }

    @RequestMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("title", "Please log in")
        return "login"
    }

    @RequestMapping("/dashboard")
    fun dashboard(model: Model, @AuthenticationPrincipal currentUser: CurrentUser): String {
        model.addAttribute("group", when (currentUser.role) {
            Role.TEACHER -> teacherService.findById(currentUser.userId).get().group.id
            Role.STUDENT -> null
            Role.ADMIN -> null
        })
        model.addAttribute("current", currentUser)
        model.addAttribute("title", "Dashboard")
        return "dashboard"
    }

    @PreAuthorize("@currentUserService.canAccessGroup(principal, #groupId)")
    @RequestMapping("/groups/{groupId}/students")
    fun getStudentsByGroupId(@PathVariable(name = "groupId") groupId: String, model: Model): String {
        model.addAttribute("title", "Students in group $groupId")
        model.addAttribute("group", groupId)
        model.addAttribute("students", studentService.findByGroupId(groupId))
        model.addAttribute("partial", Partial.values().first {
            LocalDateTime.now().month in listOf(it.first, it.second)
        })
        return "students"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/students")
    fun getStudents(@RequestParam(name = "groupId") groupId: String, model: Model): String {
        model.addAttribute("title", "Students in group $groupId")
        model.addAttribute("group", groupId)
        model.addAttribute("students", studentService.findByGroupId(groupId))
        model.addAttribute("partial", Partial.values().first {
            LocalDateTime.now().month in listOf(it.first, it.second)
        })
        return "students"
    }

    @PreAuthorize("@currentUserService.canAccessGroup(principal, #groupId)")
    @RequestMapping("/groups/{groupId}/students/{studentId}")
    fun studentForm(@PathVariable(name = "groupId") groupId: String,
                    @PathVariable(name = "studentId") studentId: String,
                    model: Model): String {
        val student = studentService.findById(studentId).get()
        val grades = studentGradeService.findByStudentId(studentId)
        model.addAttribute("student", student)
        model.addAttribute("grades", grades)
        model.addAttribute("partial", Partial.values().first {
            LocalDateTime.now().month in listOf(it.first, it.second)
        })
        return "student-form"
    }
}
