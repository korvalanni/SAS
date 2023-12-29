package ru.urfu.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/test")
class TestController {

    @GetMapping
    fun get(@RequestParam accessToken: String, @RequestParam refreshToken: String, model: Model): String {
        model.addAttribute("accessToken", accessToken)
        model.addAttribute("refreshToken", refreshToken)
        return "loginSuccess"
    }
}