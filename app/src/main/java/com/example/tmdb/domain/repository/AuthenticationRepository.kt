package com.example.tmdb.domain.repository

import com.example.tmdb.domain.model.RequestToken
import com.example.tmdb.domain.model.Session
import com.example.tmdb.util.Resource

interface AuthenticationRepository {
    suspend fun createRequestToken(redirectTo: String): Resource<RequestToken>
    suspend fun validateRequestToken(username: String, password: String, requestToken: String): Resource<RequestToken>
    suspend fun createSession(requestToken: String): Resource<Session>
}