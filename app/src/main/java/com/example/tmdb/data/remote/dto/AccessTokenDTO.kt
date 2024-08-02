package com.example.tmdb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AccessTokenDTO(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("account_id")
    val accountId: Int
)
