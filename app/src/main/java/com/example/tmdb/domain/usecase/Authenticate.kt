package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.model.Session
import com.example.tmdb.domain.repository.AuthenticationRepository
import com.example.tmdb.util.Resource
import javax.inject.Inject

//class Authenticate @Inject constructor(
//    private val repository: AuthenticationRepository
//) {
//
//    suspend fun execute(requestToken: String): Resource<Session> {
//        val accessTokenResponse = repository.createAccessToken(requestToken)
//        if (accessTokenResponse is Resource.Success) {
//            return repository.createSession(accessTokenResponse.data.accessToken)
//        }
//        return Resource.Error("Authentication Failed")
//    }
//}
