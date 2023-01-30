package com.example.movieapp.data.model.discover

data class DiscoverMoviesResponse(
    val page: Int,
    val discoverMovies: List<DiscoverMovies>,
    val total_pages: Int,
    val total_results: Int
)