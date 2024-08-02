package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.model.RequestToken
import com.example.tmdb.domain.repository.AuthenticationRepository
import com.example.tmdb.util.Resource
import javax.inject.Inject

class GetRequestToken @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend fun execute(redirectTo: String): Resource<RequestToken> {
        return repository.createRequestToken(redirectTo)
    }
}
