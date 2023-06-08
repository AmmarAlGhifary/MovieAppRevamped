package com.example.tmdb.domain.repository

import com.example.tmdb.domain.model.PersonDetail
import com.example.tmdb.domain.model.PersonList
import com.example.tmdb.util.Resource

interface PersonRepository {
    suspend fun getPersonSearchResults(query: String, page: Int): Resource<PersonList>
    suspend fun getPersonDetails(personId: Int): Resource<PersonDetail>
}