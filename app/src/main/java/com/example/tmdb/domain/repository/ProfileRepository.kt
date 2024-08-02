package com.example.tmdb.domain.repository

import com.example.tmdb.domain.model.Profile
import com.example.tmdb.util.Resource

interface ProfileRepository {
    suspend fun getProfile(sessionId : String) : Resource<Profile>
}