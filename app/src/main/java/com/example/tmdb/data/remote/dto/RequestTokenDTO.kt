package com.example.tmdb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RequestTokenDTO(
    @SerializedName("request_token")
    val requestToken: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("expires_at")
    val expiresAt: String
)
