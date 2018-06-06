package com.tormenteddan.schooldemo.configuration

import com.tormenteddan.schooldemo.domain.*
import com.tormenteddan.schooldemo.repository.*
import com.tormenteddan.schooldemo.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class InitializeDB {
    val fNames = listOf(
            "John", "Patricia",
            "Robert", "Jennifer",
            "Michael", "Linda",
            "William", "Elizabeth",
            "David", "Barbara",
            "Richard", "Susan",
            "Joseph", "Jessica",
            "Thomas", "Sarah",
            "Charles", "Margaret",
            "Christopher", "Karen",
            "Daniel", "Nancy",
            "Matthew", "Lisa",
            "Anthony", "Betty",
            "Donald", "Dorothy",
            "Mark", "Sandra",
            "Paul", "Ashley",
            "Steven", "Kimberly",
            "Andrew", "Donna",
            "Kenneth", "Emily",
            "George", "Carol",
            "Joshua", "Michelle",
            "Kevin", "Amanda",
            "Brian", "Melissa",
            "Edward", "Deborah",
            "Ronald", "Stephanie",
            "Timothy", "Rebecca",
            "Jason", "Laura",
            "Jeffrey", "Helen",
            "Ryan", "Sharon",
            "Jacob", "Cynthia",
            "Gary", "Kathleen",
            "Nicholas", "Amy",
            "Eric", "Shirley",
            "Stephen", "Angela",
            "Jonathan", "Anna",
            "Larry", "Ruth",
            "Justin", "Brenda",
            "Scott", "Pamela",
            "Brandon", "Nicole",
            "Frank", "Katherine",
            "Benjamin", "Samantha",
            "Gregory", "Christine",
            "Raymond", "Catherine",
            "Samuel", "Virginia",
            "Patrick", "Debra",
            "Alexander", "Rachel",
            "Jack", "Janet",
            "Dennis", "Emma",
            "Jerry", "Carolyn"
    )
    val lNames = listOf(
            "Smith",
            "Johnson",
            "Williams",
            "Jones",
            "Brown",
            "Davis",
            "Miller",
            "Wilson",
            "Moore",
            "Taylor",
            "Anderson",
            "Thomas",
            "Jackson",
            "White",
            "Harris",
            "Martin",
            "Thompson",
            "Garcia",
            "Martinez",
            "Robinson",
            "Clark",
            "Rodriguez",
            "Lewis",
            "Lee",
            "Walker",
            "Hall",
            "Allen",
            "Young",
            "Hernandez",
            "King",
            "Wright",
            "Lopez",
            "Hill",
            "Scott",
            "Green",
            "Adams",
            "Baker",
            "Gonzalez",
            "Nelson",
            "Carter",
            "Mitchell",
            "Perez",
            "Roberts",
            "Turner",
            "Phillips",
            "Campbell",
            "Parker",
            "Evans",
            "Edwards"
    )
    val random = Random()
    val teachers = genTeachers()
    val students = genStudents()

    private fun genStudents(): Stack<Pair<String, String>> {
        val result = Stack<Pair<String, String>>()
        for (i in 1..180) {
            val r1 = random.nextInt(fNames.size)
            val r2 = random.nextInt(lNames.size)
            result.push(fNames[r1] to lNames[r2])
        }
        return result
    }

    private fun genTeachers(): Stack<Pair<String, String>> {
        val result = Stack<Pair<String, String>>()
        for (i in 1..18) {
            val r1 = random.nextInt(fNames.size)
            val r2 = random.nextInt(lNames.size)
            result.push(fNames[r1] to lNames[r2])
        }
        return result
    }

    @Bean
    fun init(gradeRepository: GradeRepository,
             groupRepository: GroupRepository,
             teacherRepository: TeacherRepository,
             studentRepository: StudentRepository,
             studentGradeRepository: StudentGradeRepository,
             userRepository: UserRepository,
             userService: UserService
    ): CommandLineRunner {
        return CommandLineRunner {
            userRepository.deleteAll()
            gradeRepository.deleteAll()
            groupRepository.deleteAll()
            teacherRepository.deleteAll()
            studentRepository.deleteAll()
            studentGradeRepository.deleteAll()
            for (i in 1..6) {
                val grade = gradeRepository.save(Grade(i))
                val teacherList = arrayListOf<Teacher>()
                val studentList = arrayListOf<Student>()

                // Group A
                val groupA = groupRepository.save(Group("${i}A", grade))
                val (name1, last1) = teachers.pop()
                teacherList.add(teacherRepository.save(Teacher(name1, last1, groupA)))
                for (j in 1..10) {
                    val (sn, sl) = students.pop()
                    studentList.add(studentRepository.save(Student(sn, sl, groupA)))
                }

                // Group B
                val groupB = groupRepository.save(Group("${i}B", grade))
                val (name2, last2) = teachers.pop()
                teacherList.add(teacherRepository.save(Teacher(name2, last2, groupB)))
                for (j in 1..10) {
                    val (sn, sl) = students.pop()
                    studentList.add(studentRepository.save(Student(sn, sl, groupB)))
                }

                // Group C
                val groupC = groupRepository.save(Group("${i}C", grade))
                val (name3, last3) = teachers.pop()
                teacherList.add(teacherRepository.save(Teacher(name3, last3, groupC)))
                for (j in 1..10) {
                    val (sn, sl) = students.pop()
                    studentList.add(studentRepository.save(Student(sn, sl, groupC)))
                }

                teacherList.forEach {
                    userService.createUser(it)
                }
                val random = Random()
                studentList.forEach {
                    userService.createUser(it)
                    for (subject in Subject.values()) {
                        for (partial in Partial.values()) {
                            val studentGrade = StudentGrade(
                                    student = it,
                                    partial = partial,
                                    subject = subject,
                                    grade = 100 - (10 * random.nextFloat()).toInt()
                            )
                            studentGradeRepository.save(studentGrade)
                        }
                    }
                }
            }

            val admin = Admin()
            userService.createUser(admin)
        }
    }
}