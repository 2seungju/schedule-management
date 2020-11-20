package com.example.schedulemanagement.service

import com.example.schedulemanagement.model.NaverProfileSearch
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.scribejava.apis.NaverApi
import com.github.scribejava.core.builder.ServiceBuilder
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
class NaverLoginServiceImpl(): NaverLoginService {
    lateinit var clientId: String;
    lateinit var secretKey: String;
    lateinit var callback: String;
    lateinit var profileRequestUri: String;

    val objectMapper = ObjectMapper().registerKotlinModule();
    val restTemplate = RestTemplate();

    override fun createUri(request: HttpServletRequest): String {
        val state = generateState();

        request.session.setAttribute("state", state);

        val oauthService: OAuth20Service = ServiceBuilder(clientId)
                .callback(callback)
                .build(NaverApi.instance());

        return oauthService.getAuthorizationUrl(state);
    }

    private fun generateState(): String {
        val secureRandom = SecureRandom();

        return BigInteger(130, secureRandom).toString(32);
    }

    override fun checkToken(status: String, authCode: String): OAuth2AccessToken {
        val oauthService: OAuth20Service = ServiceBuilder(clientId)
                .apiSecret(secretKey)
                .build(NaverApi.instance());

        val oAuth2AccessToken: OAuth2AccessToken = oauthService.getAccessToken(authCode);

        return oAuth2AccessToken;
    }

    override fun fetchUserProfile(oAuth2AccessToken: OAuth2AccessToken): NaverProfileSearch? {
        val httpHeaders = HttpHeaders();
        httpHeaders.add("Authorization", oAuth2AccessToken.tokenType + " " + oAuth2AccessToken.accessToken)

        val httpEntity: HttpEntity<Any> = HttpEntity(httpHeaders);
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(profileRequestUri, HttpMethod.GET, httpEntity, String::class.java);

        return objectMapper.readValue<NaverProfileSearch>(responseEntity.body?: "");
    }
}
