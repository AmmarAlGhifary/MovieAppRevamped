package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.model.Profile
import com.example.tmdb.domain.repository.ProfileRepository
import com.example.tmdb.util.Resource
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val repository: ProfileRepository
){
    suspend fun invoke(sessionId: String): Resource<Profile>{
        return repository.getProfile(sessionId)
    }
}