package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.Constants
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetList @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(mediaType: MediaType, listId: String?, page: Int? = null, region: String? = null): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                MediaType.MOVIE -> if (listId == null) movieRepository.getTrendingMovies() else movieRepository.getMovieList(listId, page!!, region)
                MediaType.TV -> if (listId == null) tvRepository.getTrendingTvs() else tvRepository.getTvList(listId, page!!)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        )
    }
}