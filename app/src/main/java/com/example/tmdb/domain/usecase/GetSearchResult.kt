package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.PersonRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResult @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
    private val personRepository: PersonRepository
) {
    operator fun invoke(mediaType: MediaType, query: String, page: Int): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                MediaType.MOVIE -> movieRepository.getMovieSearchResults(query, page)
                MediaType.TV -> tvRepository.getTvSearchResults(query, page)
                MediaType.PERSON -> personRepository.getPersonSearchResults(query, page)
            }
        )
    }
}