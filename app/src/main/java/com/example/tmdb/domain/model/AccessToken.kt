package com.example.tmdb.domain.model

data class AccessToken(
    val accessToken: String,
    val success: Boolean,
    val accountId: Int
)
