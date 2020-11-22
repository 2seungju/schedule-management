package com.example.schedulemanagement.service

import com.example.schedulemanagement.model.NaverProfileSearch
import com.example.schedulemanagement.model.OAuthToken
import com.github.scribejava.core.model.OAuth2AccessToken
import javax.servlet.http.HttpServletRequest

interface NaverLoginService {
    fun createUri(request: HttpServletRequest): String;

    fun fetchToken(authCode: String): OAuthToken;

    fun fetchUserProfile(oAuthToken: OAuthToken): NaverProfileSearch;

}
