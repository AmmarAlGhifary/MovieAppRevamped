package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.Constants
import com.example.tmdb.util.MediaType
import javax.inject.Inject

class CheckFavorites  @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        id: Int
    ): Boolean = when (mediaType) {
        MediaType.MOVIE -> movieRepository.movieExists(id)
        MediaType.TV -> tvRepository.tvExists(id)
        else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
    }
}