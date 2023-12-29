package ru.urfu.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.urfu.services.user.UserService
import java.util.UUID

@Controller
class AuthWebController(
    private val userService: UserService
) {
    @GetMapping("/login")   
    fun loginMethod(@RequestParam appId: UUID?, model: Model): String {
        appId ?: return "loginRequired"

        model.addAttribute("appId", appId)
        //toDo validate application Id
        return "login"
    }

    @GetMapping("/registration")
    fun registration(@RequestParam appId: UUID, model: Model): String {
        //toDo validate application Id
        model.addAttribute("appId", appId)
        return "registration"
    }

}