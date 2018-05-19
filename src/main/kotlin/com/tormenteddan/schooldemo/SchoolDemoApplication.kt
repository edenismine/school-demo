package com.tormenteddan.schooldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchoolDemoApplication

fun main(args: Array<String>) {
    runApplication<SchoolDemoApplication>(*args)
}
