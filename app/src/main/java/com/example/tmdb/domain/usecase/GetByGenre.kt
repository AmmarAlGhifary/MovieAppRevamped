package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.Constants
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetByGenre @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(mediaType: MediaType, genreId: Int, page: Int): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                MediaType.MOVIE -> movieRepository.getMoviesByGenre(genreId, page)
                MediaType.TV -> tvRepository.getTvsByGenre(genreId, page)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        )
    }
}