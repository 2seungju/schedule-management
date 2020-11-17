package com.example.schedulemanagement.service

import com.github.scribejava.apis.NaverApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth20Service
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
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
}
