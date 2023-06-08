package com.example.tmdb.data.repository

import com.example.tmdb.data.mapper.toMovieDetail
import com.example.tmdb.data.mapper.toMovieList
import com.example.tmdb.data.mapper.toVideoList
import com.example.tmdb.data.remote.api.MovieApi
import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.model.MovieDetail
import com.example.tmdb.domain.model.MovieList
import com.example.tmdb.domain.model.VideoList
import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.util.Resource
import com.example.tmdb.util.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val safeApiCall : SafeApiCall,
//    private val dao
) : MovieRepository {

    override suspend fun getMovieList(listId: String, page: Int, region: String): Resource<MovieList> = safeApiCall.execute {
        api.getMovieList(listId, page, region).toMovieList()
    }

    override suspend fun getTrendingMovies(): Resource<MovieList> = safeApiCall.execute {
        api.getTrendingMovies().toMovieList()
    }

    override suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList> = safeApiCall.execute {
        api.getTrendingMovieTrailers(movieId).toVideoList()
    }

    override suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<MovieList> = safeApiCall.execute {
        api.getMoviesByGenre(genreId, page).toMovieList()
    }

    override suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList> = safeApiCall.execute {
        api.getMovieSearchResults(query, page).toMovieList()
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> = safeApiCall.execute {
        api.getMovieDetails(movieId).toMovieDetail()
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        TODO("Not yet implemented")
    }

    override suspend fun movieExists(movieId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovie(movie: FavoriteMovie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(movie: FavoriteMovie) {
        TODO("Not yet implemented")
    }


}