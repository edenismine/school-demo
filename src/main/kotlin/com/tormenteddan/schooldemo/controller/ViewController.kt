package com.tormenteddan.schooldemo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class ViewController {
    @GetMapping("/login")
    fun getLoginPage(@RequestParam error: Optional<String>): ModelAndView {
        return ModelAndView("login", "error", error)
    }

    @GetMapping("/index")
    fun getIndex() = "index"
}