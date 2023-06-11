package com.example.tmdb.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.usecase.AddFavorites
import com.example.tmdb.domain.usecase.DeleteFavorites
import com.example.tmdb.domain.usecase.GetFavorites
import com.example.tmdb.util.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val deleteFavorite: DeleteFavorites,
    private val addFavorite: AddFavorites
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow(emptyList<FavoriteMovie>())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            getFavorites(MediaType.MOVIE).collect {
                _favoriteMovies.value = it as List<FavoriteMovie>
            }
        }
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            deleteFavorite(mediaType = MediaType.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            addFavorite(mediaType = MediaType.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }

    init {
        fetchFavoriteMovies()
    }
}