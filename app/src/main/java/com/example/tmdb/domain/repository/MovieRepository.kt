package com.example.tmdb.domain.repository

import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.model.MovieDetail
import com.example.tmdb.domain.model.MovieList
import com.example.tmdb.domain.model.VideoList
import com.example.tmdb.util.Resource

interface MovieRepository {

    suspend fun getMovieList(listId : String, page: Int, region: String): Resource<MovieList>

    suspend fun getTrendingMovies(): Resource<MovieList>

    suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList>

    suspend fun getMoviesByGenre(genreId: Int, page: Int): Resource<MovieList>

    suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList>

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail>

    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    suspend fun movieExists(movieId: Int): Boolean

    suspend fun insertMovie(movie: FavoriteMovie)

    suspend fun deleteMovie(movie: FavoriteMovie)

}