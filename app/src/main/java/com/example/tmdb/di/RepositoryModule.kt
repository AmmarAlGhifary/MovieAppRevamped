package com.example.tmdb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
//    @Binds
//    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository
//
//    @Binds
//    abstract fun bindTvRepository(repository: TvRepositoryImpl): TvRepository
//
//    @Binds
//    abstract fun bindPersonRepository(repository: PersonRepositoryImpl): PersonRepository
}