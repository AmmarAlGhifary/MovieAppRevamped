package com.example.movieapp.data.di.repository

import android.util.Log
import com.example.movieapp.data.di.network.ApiService
import com.example.movieapp.data.model.genres.GenreResponse
import com.example.movieapp.utils.Resource
import okio.IOException
import javax.inject.Inject

class GenresRepository @Inject constructor(private val apiService: ApiService) {

    fun getGenres(): Resource<GenreResponse> {
        return try {
            val response = apiService.getGenreList()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Log.e(" genreRepository", "Error getting quotes: ${response.code()}")
                Resource.Error(IOException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e(" genreRepository", "Error getting quotes: ${e.message}")
            Resource.Error(e)
        }
    }
}