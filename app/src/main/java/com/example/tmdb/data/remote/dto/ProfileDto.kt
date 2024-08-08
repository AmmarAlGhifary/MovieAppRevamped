package com.example.tmdb.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("avatar")
    val avatar: AvatarDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("iso_639_1")
    val language: String,
    @SerializedName("iso_3166_1")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("include_adult")
    val includeAdult: Boolean,
    @SerializedName("username")
    val username: String
)

data class AvatarDto(
    @SerializedName("gravatar")
    val gravatar: GravatarDto,
    @SerializedName("tmdb")
    val tmdb: TmdbAvatarDto?
)

data class GravatarDto(
    @SerializedName("hash")
    val hash: String
)

data class TmdbAvatarDto(
    @SerializedName("avatar_path")
    val avatarPath: String?
)
