package com.example.tmdb.ui.favoritetv

import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.FavoriteTv
import com.example.tmdb.domain.usecase.AddFavorites
import com.example.tmdb.domain.usecase.DeleteFavorites
import com.example.tmdb.domain.usecase.GetFavorites
import com.example.tmdb.ui.base.BaseViewModel
import com.example.tmdb.util.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteTvViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val deleteFavorites: DeleteFavorites,
    private val addFavorites: AddFavorites
) : BaseViewModel() {

    private val _favoritesTv = MutableStateFlow(emptyList<FavoriteTv>())
    val favoriesTv get() = _favoritesTv.asStateFlow()

    init {
        fetchFavoriteTvs()
    }

    fun fetchFavoriteTvs() {
        viewModelScope.launch {
            getFavorites(MediaType.TV).collect {
                _favoritesTv.value = it as List<FavoriteTv>
            }
        }
    }

    fun removeTvFromFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            deleteFavorites(mediaType = MediaType.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }

    fun addTvToFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            addFavorites(mediaType = MediaType.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }


}