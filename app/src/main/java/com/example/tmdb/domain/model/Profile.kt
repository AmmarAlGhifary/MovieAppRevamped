package com.example.tmdb.domain.model

data class Profile(
    val avatar: Avatar,
    val id: Int,
    val language: String,
    val country: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)

data class Avatar(
    val gravatar: Gravatar,
    val tmdb: TmdbAvatar?
)

data class Gravatar(
    val hash: String
)

data class TmdbAvatar(
    val avatarPath: String?
)
