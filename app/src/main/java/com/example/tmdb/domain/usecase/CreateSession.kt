package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.model.Session
import com.example.tmdb.domain.repository.AuthenticationRepository
import com.example.tmdb.util.Resource
import javax.inject.Inject

class CreateSession @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend fun execute(requestToken: String): Resource<Session> {
        return repository.createSession(requestToken)
    }
}
