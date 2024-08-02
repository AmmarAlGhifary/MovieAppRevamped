package com.example.tmdb.domain.model

data class Profile(
    val avatarUrl: String,
    val id: Int,
    val language: String,
    val country: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)
