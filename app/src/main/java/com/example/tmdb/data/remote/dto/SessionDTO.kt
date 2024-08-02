package com.example.tmdb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SessionDTO(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("success")
    val success: Boolean
)
