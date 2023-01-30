package com.example.movieapp.data.di.network

import com.example.movieapp.BuildConfig.API_KEY
import com.example.movieapp.data.model.discover.DiscoverMoviesResponse
import com.example.movieapp.data.model.genres.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun getPopularMovies(
        @Query("api_key") apiKey : String = API_KEY,
        @Query("with_genres") genreId: Int
    ): Response<DiscoverMoviesResponse>

    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey : String = API_KEY,
    ): Response<GenreResponse>
}