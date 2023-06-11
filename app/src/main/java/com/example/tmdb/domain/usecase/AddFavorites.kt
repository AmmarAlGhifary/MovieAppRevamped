package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.model.FavoriteTv
import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.Constants
import com.example.tmdb.util.MediaType
import javax.inject.Inject

class AddFavorites @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        movie: FavoriteMovie? = null,
        tv: FavoriteTv? = null
    ) {
        when (mediaType) {
            MediaType.MOVIE -> movieRepository.insertMovie(movie!!)
            MediaType.TV -> tvRepository.insertTv(tv!!)
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
        }
    }
}