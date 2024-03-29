package com.example.tmdb.domain.usecase

import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.PersonRepository
import com.example.tmdb.domain.repository.TvRepository
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetails @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
    private val personRepository: PersonRepository
) {

    operator fun invoke(
        mediaType: MediaType,
        id: Int,
        seasonNumber: Int? = null,
        episodeNumber: Int? = null
    ): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                MediaType.MOVIE -> movieRepository.getMovieDetails(id)
                MediaType.TV ->
                    if (seasonNumber != null) {
                        if (episodeNumber != null) tvRepository.getEpisodeDetails(
                            id,
                            seasonNumber,
                            episodeNumber
                        )
                        else tvRepository.getSeasonDetails(id, seasonNumber)
                    } else tvRepository.getTvDetails(id)
                MediaType.PERSON -> personRepository.getPersonDetails(id)
            }
        )
    }
}