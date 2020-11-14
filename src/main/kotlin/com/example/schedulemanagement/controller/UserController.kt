package com.example.schedulemanagement.controller

import com.example.schedulemanagement.service.NaverLoginService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController(private val naverLoginService: NaverLoginService) {

    @GetMapping("login")
    fun login(request: HttpServletRequest): String {
        val authUri = naverLoginService.createUri(request);
        naverLoginService.login(authUri);

        return "login";
    }

    @GetMapping("callback")
    fun callback(): String {
        return "";
    }
}
