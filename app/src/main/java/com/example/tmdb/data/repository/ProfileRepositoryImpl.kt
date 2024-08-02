package com.example.tmdb.data.repository

import com.example.tmdb.data.mapper.toProfile
import com.example.tmdb.data.remote.api.ProfileApi
import com.example.tmdb.domain.model.Profile
import com.example.tmdb.domain.repository.ProfileRepository
import com.example.tmdb.util.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository{

    override suspend fun getProfile(sessionId: String): Resource<Profile> {
        return try {
            val response = api.getProfile(sessionId)
            Resource.Success(response.toProfile())
        } catch (e: Exception){
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

}