package com.example.tmdb.data.mapper

import com.example.tmdb.data.local.entity.FavoriteMovieEntity
import com.example.tmdb.data.local.entity.FavoriteTvEntity
import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.model.FavoriteTv


fun FavoriteMovie.toFavoriteMovieEntity() = FavoriteMovieEntity(id, posterPath, releaseDate, runtime, title, voteAverage, voteCount, date)

fun FavoriteMovieEntity.toFavoriteMovie() = FavoriteMovie(id, posterPath, releaseDate, runtime, title, voteAverage, voteCount, date)

fun FavoriteTv.toFavoriteTvEntity() = FavoriteTvEntity(id, episodeRunTime, firstAirDate, name, posterPath, voteAverage, voteCount, date)

fun FavoriteTvEntity.toFavoriteTv() = FavoriteTv(id, episodeRunTime, firstAirDate, name, posterPath, voteAverage, voteCount, date)