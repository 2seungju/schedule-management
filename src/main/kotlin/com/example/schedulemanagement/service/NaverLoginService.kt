package com.example.schedulemanagement.service

import javax.servlet.http.HttpServletRequest

interface NaverLoginService {
    fun createUri(request: HttpServletRequest): String;

//    fun login(authUri: String);
}
