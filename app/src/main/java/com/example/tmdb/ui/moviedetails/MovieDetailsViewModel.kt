package com.example.tmdb.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.MovieDetail
import com.example.tmdb.domain.usecase.GetDetails
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
    private val getDetails: GetDetails
) : BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()

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

    fun initRequests(movieId: Int) {
        id = movieId
        fetchMovieDetails()
    }
}