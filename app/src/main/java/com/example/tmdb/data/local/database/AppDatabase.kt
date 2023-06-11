package com.example.tmdb.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.local.dao.MovieDao
import com.example.tmdb.data.local.dao.TvDao
import com.example.tmdb.data.local.entity.FavoriteMovieEntity
import com.example.tmdb.data.local.entity.FavoriteTvEntity

@Database(entities = [FavoriteMovieEntity::class, FavoriteTvEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvDao(): TvDao
}
