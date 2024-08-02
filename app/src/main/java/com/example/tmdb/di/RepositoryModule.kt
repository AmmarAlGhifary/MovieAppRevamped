package com.example.tmdb.di

import com.example.tmdb.data.repository.AuthenticationRepositoryImpl
import com.example.tmdb.data.repository.MovieRepositoryImpl
import com.example.tmdb.data.repository.PersonRepositoryImpl
import com.example.tmdb.data.repository.ProfileRepositoryImpl
import com.example.tmdb.data.repository.TvRepositoryImpl
import com.example.tmdb.domain.repository.AuthenticationRepository
import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.repository.PersonRepository
import com.example.tmdb.domain.repository.ProfileRepository
import com.example.tmdb.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindTvRepository(repository: TvRepositoryImpl): TvRepository

    @Binds
    abstract fun bindPersonRepository(repository: PersonRepositoryImpl): PersonRepository

    @Binds
    abstract fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepositoryImpl) : ProfileRepository
}