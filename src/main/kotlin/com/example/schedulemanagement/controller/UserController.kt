package com.example.schedulemanagement.controller

import com.example.schedulemanagement.model.NaverProfileSearch
import com.example.schedulemanagement.service.NaverLoginService
import com.github.scribejava.core.model.OAuth2AccessToken
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
    fun callback(request: HttpServletRequest, @RequestParam state: String, @RequestParam code: String): ResponseEntity<Any> {
        val storedState: String = request.session.getAttribute("state") as String;


        if (storedState != state) {
            return ResponseEntity("Token is invalid." ,HttpStatus.BAD_REQUEST);
        }

        val responseEntity: ResponseEntity<Any> = ResponseEntity(HttpStatus.OK);

        val oAuth2AccessToken: OAuth2AccessToken = naverLoginService.checkToken(code);
        val naverProfileSearch: NaverProfileSearch = naverLoginService.fetchUserProfile(oAuth2AccessToken);

        println(naverProfileSearch);

        return responseEntity;
    }
}
