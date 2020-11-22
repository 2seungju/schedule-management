package com.example.schedulemanagement.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverProfileSearch(
        @JsonProperty("resultcode")
        val resultCode: String?,
        val message: String?,
        val response: ProfileResponse?
        )

data class ProfileResponse(
        val email: String?,
        val nickname: String?,
        @JsonProperty("profile_image")
        val profileImage: String?,
        val age: String?,
        val gender: String?,
        val id: String?,
        val name: String?,
        val birthday: String?
)