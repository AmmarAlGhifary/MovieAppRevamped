package com.example.tmdb.data.remote.api

import com.example.tmdb.data.remote.dto.ProfileDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("account")
    suspend fun getProfile(
        @Query("session_id") sessionId : String
    ) : ProfileDto
}