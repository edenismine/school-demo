package com.tormenteddan.schooldemo.services

import com.tormenteddan.schooldemo.domain.Subject
import org.springframework.stereotype.Service

@Service
class SubjectService {
    fun findAll(): List<Subject> = Subject.values().toList()

    fun getName(subject: Subject): String {
        return when(subject){
            Subject.SPANISH -> "Spanish"
            Subject.MATH -> "Math"
            Subject.NAT_SC -> "Nat. Science"
            Subject.SOCIAL_SC -> "Social Sciences"
            Subject.PE -> "Physical Education"
            Subject.ENGLISH -> "English"
        }
    }

    fun getId(subject: Subject): String {
        return when(subject){
            Subject.SPANISH -> "spanish"
            Subject.MATH -> "math"
            Subject.NAT_SC -> "natSc"
            Subject.SOCIAL_SC -> "socialSc"
            Subject.PE -> "pE"
            Subject.ENGLISH -> "english"
        }
    }
}