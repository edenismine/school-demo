package com.tormenteddan.schooldemo.controller

import com.tormenteddan.schooldemo.domain.*
import com.tormenteddan.schooldemo.services.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.Year


@Controller
class MainController(
        val studentService: StudentService,
        val teacherService: TeacherService,
        val partialService: PartialService,
        val studentGradeService: StudentGradeService,
        val userService: UserService
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
    fun dashboard(model: Model,
                  @AuthenticationPrincipal currentUser: CurrentUser,
                  @RequestParam(name = "partial", required = false) partial: Int?,
                  @RequestParam(name = "partial", required = false) year: Int?): String {
        val user = when (currentUser.role) {
            Role.TEACHER -> teacherService.findById(currentUser.userId).get()
            Role.STUDENT -> studentService.findById(currentUser.userId).get()
            Role.ADMIN -> null
        }
        model.addAttribute("user", when(user){
            is Student -> user
            is Teacher -> user
            else -> null
        })
        model.addAttribute("group", when(user){
            is Student -> user.group.id
            is Teacher -> user.group.id
            else -> null
        })
        if(user is Student) {
            val grades = studentGradeService.findByStudentId(user.id)
            val years = grades.map { it.year }.distinct()
            val validYear = if (year != null && year in years) year else Year.now().value
            val periods = grades.map { it.year to it.partial }.distinct()

            model.addAttribute("periods", periods)
            if(partial != null){
                model.addAttribute("partial", partialService.findPartial(partial))
            }
            model.addAttribute("year", validYear)
            model.addAttribute("years", years)
        }
        model.addAttribute("title", "Dashboard")
        return "dashboard"
    }

    /**
     * Allows a teacher or administrator to check the grades of students in a given group for a given partial.
     *
     * @param groupId The group's id.
     * @param partial The partial that's been consulted.
     * @param model The model.
     */
    @PreAuthorize("@currentUserService.canAccess(principal, #groupId, null )")
    @RequestMapping("/groups/{groupId}/students")
    fun getStudentsByGroupId(@PathVariable(name = "groupId") groupId: String,
                             @RequestParam(name = "partial", required = false) partial: Int?,
                             model: Model): String {
        model.addAttribute("title", "Students in group $groupId")
        model.addAttribute("group", groupId)
        model.addAttribute("students", studentService.findByGroupId(groupId))
        model.addAttribute("partial", partialService.findPartial(partial))
        model.addAttribute("year", Year.now().value)
        return "group-grades"
    }

    /**
     * Allows a teacher or administrator to edit a student's grades for the specified partial of the current year.
     *
     * @param groupId The student's group's id.
     * @param studentId The student's id.
     * @param partial The partial that's been consulted.
     * @param model The model.
     */
    @PreAuthorize("@currentUserService.canAccess(principal, #groupId, #studentID) && hasAnyAuthority('ADMIN', 'TEACHER')")
    @GetMapping("/groups/{groupId}/students/{studentId}")
    fun getEditGrades(@PathVariable(name = "groupId") groupId: String,
                      @PathVariable(name = "studentId") studentId: String,
                      @RequestParam(name = "partial", required = false) partial: Int?,
                      model: Model): String {
        val student = studentService.findById(studentId).get()
        val defPartial = partialService.findPartial(partial)
        val grades = studentGradeService.findByStudentIdAndPartialAndYear(studentId, defPartial)
        model.addAttribute("title", "Grade student $studentId")
        model.addAttribute("student", student)
        model.addAttribute("grades", grades)
        model.addAttribute("partial", defPartial)
        model.addAttribute("year", Year.now().value)
        model.addAttribute("form", GradingForm(student.id, defPartial.ordinal))
        return "edit-grades"
    }

    /**
     * Allows a teacher or administrator to edit a student's grades for the specified partial of the current year.
     *
     * @param groupId The student's group's id.
     * @param studentId The student's id.
     * @param partial The partial that's been consulted.
     * @param model The model.
     */
    @PreAuthorize("@currentUserService.canAccess(principal, #groupId, #studentID) && hasAnyAuthority('ADMIN', 'TEACHER')")
    @PostMapping("/groups/{groupId}/students/{studentId}")
    fun postEditGrades(@PathVariable(name = "groupId") groupId: String,
                       @PathVariable(name = "studentId") studentId: String,
                       @RequestParam(name = "partial", required = false) partial: Int?,
                       @ModelAttribute gradingForm: GradingForm,
                       model: Model): String {
        val student = studentService.findById(studentId).get()
        val defPartial = partialService.findPartial(partial)
        val grades = studentGradeService.submitGrades(gradingForm)
        model.addAttribute("title", "Grade student $studentId")
        model.addAttribute("student", student)
        model.addAttribute("grades", grades)
        model.addAttribute("partial", defPartial)
        model.addAttribute("year", Year.now().value)
        return "edit-grades"
    }

    /**
     * Allows an administrator to edit a student.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/students/{studentId}")
    fun editStudent(@PathVariable(name = "studentId") studentId: String,
                    model: Model): String {
        val student = studentService.findById(studentId).get()
        model.addAttribute("student", student)
        model.addAttribute("title", "Edit student")
        return "edit-student"
    }

    /**
     * Allows an administrator to edit a teacher.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/teachers/{teacherId}")
    fun editTeacher(@PathVariable(name = "teacherId") teacherId: String,
                    model: Model): String {
        val teacher = teacherService.findById(teacherId).get()
        model.addAttribute("teacher", teacher)
        model.addAttribute("title", "Edit teacher")
        return "edit-teacher"
    }


    /**
     * Allows an administrator to add a new teacher.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/teachers/new")
    fun newTeacherForm(model: Model): String {
        model.addAttribute("teacher", UserForm())
        model.addAttribute("title", "Add new Teacher")
        return "new-teacher"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/teachers/new")
    fun newTeacher(@ModelAttribute teacherForm: UserForm,
                   model: Model): String {
        val teacher = teacherService.newTeacher(teacherForm)
        return if (teacher != teacherService.noTeacher) {
            userService.createUser(teacher)
            teacherService.save(teacher)
            println(teacher)
            model.addAttribute("title", "Teacher added successfully")
            model.addAttribute("teacher", teacher)
            "new-teacher-success" // return
        } else {
            "new-teacher" // return
        }
    }

    /**
     * Allows an administrator to add a new teacher.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/students/new")
    fun newStudentForm(model: Model): String {
        model.addAttribute("student", UserForm())
        model.addAttribute("title", "Add new student")
        return "new-student"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/students/new")
    fun newStudent(@ModelAttribute studentForm: UserForm,
                   model: Model): String {
        val student = studentService.newStudent(studentForm)
        studentService.save(student)
        userService.createUser(student)
        println(student)
        model.addAttribute("title", "Student added successfully")
        model.addAttribute("student", student)
        return "new-student-success"
    }
}
