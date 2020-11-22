package com.example.schedulemanagement.service

import com.example.schedulemanagement.common.NaverOAuthDomain
import com.example.schedulemanagement.model.NaverProfileSearch
import com.example.schedulemanagement.model.OAuthToken
import com.example.schedulemanagement.utils.OAuthUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.scribejava.apis.NaverApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.math.BigInteger
import java.security.SecureRandom
import javax.servlet.http.HttpServletRequest

@Service
@ConfigurationProperties(prefix = "naver.login")
class NaverLoginServiceImpl: NaverLoginService {
    private val objectMapper = ObjectMapper().registerKotlinModule();
    private val restTemplate = RestTemplate();

    lateinit var clientId: String;
    lateinit var secretKey: String;
    lateinit var callback: String;

    override fun createUri(request: HttpServletRequest): String {
        val state = generateState();

        request.session.setAttribute("state", state);

        val oAuthUtil: OAuthUtil = OAuthUtil.Builder()
                .clientId(clientId)
                .callback(callback)
                .state(state)
                .build();

        return oAuthUtil.createAuthorizationUri();
    }

    private fun generateState(): String {
        val secureRandom = SecureRandom();

        return BigInteger(130, secureRandom).toString(32);
    }

    override fun fetchToken(authCode: String): OAuthToken {
        val oAuthUtil: OAuthUtil = OAuthUtil.Builder()
                .clientId(clientId)
                .secretKey(secretKey)
                .callback(callback)
                .code(authCode)
                .build();

        return oAuthUtil.fetchOAuthToken();
    }

    override fun fetchUserProfile(oAuthToken: OAuthToken): NaverProfileSearch {
        val httpHeaders = HttpHeaders();
        httpHeaders.add("Authorization", oAuthToken.tokenType + " " + oAuthToken.accessToken)

        val httpEntity: HttpEntity<Any> = HttpEntity(httpHeaders);
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(NaverOAuthDomain.USER_PROFILE.domain, HttpMethod.GET, httpEntity, String::class.java);

        return objectMapper.readValue(responseEntity.body?: "");
    }

    private fun refreshToken(refreshToken: String): OAuthToken {
        val oAuthUtil: OAuthUtil = OAuthUtil.Builder()
                .clientId(clientId)
                .secretKey(secretKey)
                .build();

        return oAuthUtil.refreshOAuthToken(refreshToken);
    }
}
