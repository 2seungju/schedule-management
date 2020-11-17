package com.example.schedulemanagement.controller

import com.example.schedulemanagement.service.NaverLoginService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/user")
class UserController(private val naverLoginService: NaverLoginService) {

    @GetMapping("login", produces = [MediaType.TEXT_HTML_VALUE])
    fun login(request: HttpServletRequest): String {
        val authUri = naverLoginService.createUri(request);

        return "redirect:$authUri";
    }

    @GetMapping("callback")
    @ResponseBody
    fun callback(request: HttpServletRequest, @RequestParam state: String, @RequestParam code: String): String {
        val storedState: String = request.session.getAttribute("state") as String;

        var statusCode = 200;

        if (storedState != state) {
            statusCode = 400
        }

        return statusCode.toString();
    }
}
