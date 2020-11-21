package com.example.schedulemanagement.service

import com.example.schedulemanagement.model.NaverProfileSearch
import com.github.scribejava.core.model.OAuth2AccessToken
import javax.servlet.http.HttpServletRequest

interface NaverLoginService {
    fun createUri(request: HttpServletRequest): String;

    fun checkToken(authCode: String): OAuth2AccessToken;

    fun fetchUserProfile(oAuth2AccessToken: OAuth2AccessToken): NaverProfileSearch;
}
