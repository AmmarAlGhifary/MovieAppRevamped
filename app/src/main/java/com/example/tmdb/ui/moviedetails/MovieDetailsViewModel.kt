package com.example.tmdb.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.domain.model.MovieDetail
import com.example.tmdb.domain.usecase.*
import com.example.tmdb.ui.base.BaseViewModel
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.Resource
import com.example.tmdb.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
    private val checkFavorites: CheckFavorites,
    private val deleteFavorites: DeleteFavorites,
    private val addFavorites: AddFavorites
) : BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites get() = _isInFavorites.asStateFlow()

    private lateinit var favoriteMovie: FavoriteMovie

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getDetails(MediaType.MOVIE, id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as MovieDetail).apply {
                            _details.value = this
                        }
                        _uiState.value = UiState.successState()
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    private fun checkFavorites() {
        viewModelScope.launch {
            _isInFavorites.value = checkFavorites(MediaType.MOVIE, id)
        }
    }

    fun updateFavorites() {
        viewModelScope.launch {
            if (_isInFavorites.value) {
                deleteFavorites(mediaType = MediaType.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = false
            } else {
                addFavorites(mediaType = MediaType.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = true
            }
        }
    }
    fun initRequests(movieId: Int) {
        id = movieId
        fetchMovieDetails()
        checkFavorites()
    }
}