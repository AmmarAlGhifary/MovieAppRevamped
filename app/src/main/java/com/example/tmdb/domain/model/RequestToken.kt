package com.example.tmdb.domain.model

data class RequestToken(
    val requestToken: String,
    val success: Boolean,
    val expiresAt: String
)
