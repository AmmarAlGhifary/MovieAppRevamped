package com.example.tmdb.ui.seasondetails

import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.EpisodeDetail
import com.example.tmdb.domain.model.SeasonDetail
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
class SeasonDetailsViewModel @Inject constructor(private val getDetails: GetDetails) : BaseViewModel() {

    private val _seasonDetails = MutableStateFlow(SeasonDetail.empty)
    val seasonDetails get() = _seasonDetails.asStateFlow()

    private val _episodeDetails = MutableStateFlow(EpisodeDetail.empty)
    val episodeDetails get() = _episodeDetails.asStateFlow()

    private fun fetchSeasonDetails(seasonNumber: Int) {
        viewModelScope.launch {
            getDetails(MediaType.TV, id, seasonNumber).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _seasonDetails.value = response.data as SeasonDetail
                        _uiState.value = UiState.successState()
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    fun fetchEpisodeDetails(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            getDetails(MediaType.TV, id, seasonNumber, episodeNumber).collect { response ->
                when (response) {
                    is Resource.Success -> _episodeDetails.value = response.data as EpisodeDetail
                    is Resource.Error -> _uiState.value = UiState.errorState(false, response.message)
                }
            }
        }
    }

    fun clearEpisodeData() {
        _episodeDetails.value = EpisodeDetail.empty
    }

    fun initRequest(tvId: Int, seasonNumber: Int) {
        id = tvId
        fetchSeasonDetails(seasonNumber)
    }

}