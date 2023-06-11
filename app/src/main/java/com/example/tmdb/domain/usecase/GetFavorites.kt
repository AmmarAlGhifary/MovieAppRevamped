package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.Constants
import com.example.tmdb.util.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavorites @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(mediaType: MediaType): Flow<List<Any>> = flow {
        emit(
            when (mediaType) {
                MediaType.MOVIE -> movieRepository.getFavoriteMovies()
                MediaType.TV -> tvRepository.getFavoriteTvs()
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        )
    }
}