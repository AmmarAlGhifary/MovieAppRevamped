package com.example.tmdb.data.repository

import com.example.tmdb.BuildConfig
import com.example.tmdb.data.mapper.toRequestToken
import com.example.tmdb.data.mapper.toSession
import com.example.tmdb.data.remote.api.AuthenticationApi
import com.example.tmdb.domain.model.RequestToken
import com.example.tmdb.domain.model.Session
import com.example.tmdb.domain.repository.AuthenticationRepository
import com.example.tmdb.util.Resource
import com.example.tmdb.util.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val api: AuthenticationApi,
    private val safeApiCall: SafeApiCall
) : AuthenticationRepository {

    private val authHeader = "Bearer ${BuildConfig.API_KEY}"

    override suspend fun createRequestToken(redirectTo: String): Resource<RequestToken> = safeApiCall.execute {
        api.createRequestToken(authHeader).toRequestToken()
    }

    override suspend fun validateRequestToken(username: String, password: String, requestToken: String): Resource<RequestToken> = safeApiCall.execute {
        api.validateRequestToken(authHeader, mapOf(
            "username" to username,
            "password" to password,
            "request_token" to requestToken
        )).toRequestToken()
    }

    override suspend fun createSession(requestToken: String): Resource<Session> = safeApiCall.execute {
        api.createSession(authHeader, mapOf("request_token" to requestToken)).toSession()
    }
}
