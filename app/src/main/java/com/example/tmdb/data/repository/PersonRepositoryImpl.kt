package com.example.tmdb.data.repository

import com.example.tmdb.data.mapper.toPersonDetail
import com.example.tmdb.data.mapper.toPersonList
import com.example.tmdb.data.remote.api.PersonApi
import com.example.tmdb.domain.model.PersonDetail
import com.example.tmdb.domain.model.PersonList
import com.example.tmdb.domain.repository.PersonRepository
import com.example.tmdb.util.Resource
import com.example.tmdb.util.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val api: PersonApi,
    private val safeApiCall: SafeApiCall
) : PersonRepository {
    override suspend fun getPersonSearchResults(query: String, page: Int): Resource<PersonList> = safeApiCall.execute {
        api.getPersonSearchResults(query, page).toPersonList()
    }

    override suspend fun getPersonDetails(personId: Int): Resource<PersonDetail> = safeApiCall.execute {
        api.getPersonDetails(personId).toPersonDetail()
    }
}