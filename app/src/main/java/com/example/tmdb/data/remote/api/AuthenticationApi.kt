package com.example.tmdb.data.remote.api

import com.example.tmdb.data.remote.dto.RequestTokenDTO
import com.example.tmdb.data.remote.dto.SessionDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationApi {
    @GET("authentication/token/new")
    suspend fun createRequestToken(
        @Header("Authorization") authHeader: String
    ): RequestTokenDTO

    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestToken(
        @Header("Authorization") authHeader: String,
        @Body body: Map<String, String>
    ): RequestTokenDTO

    @POST("authentication/session/new")
    suspend fun createSession(
        @Header("Authorization") authHeader: String,
        @Body body: Map<String, String>
    ): SessionDTO
}