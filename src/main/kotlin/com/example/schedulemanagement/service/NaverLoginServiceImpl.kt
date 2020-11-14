package com.example.schedulemanagement.service

import com.github.scribejava.apis.NaverApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth20Service
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.math.BigInteger
import java.security.SecureRandom
import javax.servlet.http.HttpServletRequest

@Service
class NaverLoginServiceImpl(): NaverLoginService {

    override fun createUri(request: HttpServletRequest): String {
        val state = generateState();

        request.session.setAttribute("state", state);

        val oauthService: OAuth20Service = ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET_KEY)
                .callback(CALLBACK_URL)
                .build(NaverApi.instance());

        println(oauthService.getAuthorizationUrl(state))
        return oauthService.getAuthorizationUrl(state);
    }

    override fun login(authUri: String) {
        val restTemplate = RestTemplate();
        // TODO: HttpEntity 기본 생성자는 protected이기 때문에 초기화가 안됨
        val httpEntity:HttpEntity<String> = HttpEntity("");
        restTemplate.exchange<Any>(authUri, HttpMethod.GET, httpEntity, String);
    }

    private fun generateState(): String {
        val secureRandom = SecureRandom();

        return BigInteger(130, secureRandom).toString(32);
    }

    companion object {
        const val CLIENT_ID: String = "3COH0mAfzZJz0LJjzsgi";
        const val CLIENT_SECRET_KEY: String = "prGQdUQFm1";
        const val CALLBACK_URL: String = "http://localhost:8080/user/callback";
    }
}
