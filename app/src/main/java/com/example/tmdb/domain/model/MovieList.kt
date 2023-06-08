package com.example.tmdb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieList (
    val result: List<Movie>,
    val totalResult: Int
) {
    companion object {
        val empty = MovieList(
            result = emptyList(),
            totalResult = 0
        )
    }
}

@Parcelize
data class Movie(
    val character: String?,
    val id: Int,
    val job: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Double
) : Parcelable