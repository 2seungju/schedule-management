package com.example.schedulemanagement.common

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NaverOAuthDomainTest {
    @Test
    fun testEum() {
        assertEquals(NaverOAuthDomain.AUTH.domain, "https://nid.naver.com/oauth2.0/authorize?response_type=code&redirect_uri&");
    }
}