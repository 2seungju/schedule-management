package com.example.schedulemanagement.controller

import com.example.schedulemanagement.service.MainService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MainController(private val mainService: MainService) {

    @GetMapping("test")
    fun test(): String {
        return mainService.getUser();
    }
}
