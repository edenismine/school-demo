package com.tormenteddan.schooldemo.configuration

import com.tormenteddan.schooldemo.domain.Admin
import com.tormenteddan.schooldemo.domain.Grade
import com.tormenteddan.schooldemo.domain.Group
import com.tormenteddan.schooldemo.domain.Teacher
import com.tormenteddan.schooldemo.repository.GradeRepository
import com.tormenteddan.schooldemo.repository.GroupRepository
import com.tormenteddan.schooldemo.repository.TeacherRepository
import com.tormenteddan.schooldemo.repository.UserRepository
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
             userRepository: UserRepository,
             userService: UserService
    ): CommandLineRunner {
        return CommandLineRunner {
            userRepository.deleteAll()
            gradeRepository.deleteAll()
            groupRepository.deleteAll()
            teacherRepository.deleteAll()
            for (i in 1..6) {
                val grade = gradeRepository.save(Grade(i))
                val (name1, last1) = teachers.pop()
                val teacher1 = teacherRepository.save(Teacher(name1, last1,
                        groupRepository.save(Group("${i}A", grade))))
                val (name2, last2) = teachers.pop()
                val teacher2 = teacherRepository.save(Teacher(name2, last2,
                        groupRepository.save(Group("${i}B", grade))))
                val (name3, last3) = teachers.pop()
                val teacher3 = teacherRepository.save(Teacher(name3, last3,
                        groupRepository.save(Group("${i}C", grade))))
                userService.createUser(teacher1)
                userService.createUser(teacher2)
                userService.createUser(teacher3)
            }
            val admin = Admin()
            userService.createUser(admin)
            println(admin)
        }
    }
}