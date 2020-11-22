package com.example.schedulemanagement.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthToken(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("refresh_token")
        val refreshToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("expires_in")
        val expiresIn: String
)