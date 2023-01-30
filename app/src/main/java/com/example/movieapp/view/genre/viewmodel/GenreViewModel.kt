@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.movieapp.view.genre.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.di.repository.GenresRepository
import com.example.movieapp.data.model.genres.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.movieapp.utils.Resource
import com.example.movieapp.utils.Resource.*
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val repository: GenresRepository) : ViewModel() {

    private val _response : MutableLiveData<Genre?> = MutableLiveData()
    val genreResponse : LiveData<Genre?>
        get() = _response

    private val _error : MutableLiveData<String> = MutableLiveData()
    val error : LiveData<String>
        get() = _error

    fun getGenres() = viewModelScope.launch {
        try {
            val genreResult = repository.getGenres()
            if (genreResult is Success) {
                _response.postValue(genreResult.data as Genre?)
            } else if (genreResult is Error) {
                _error.postValue("Error: ${genreResult.exception.message}")
                Log.e(" genreViewModel", "Error getting quotes: ${genreResult.exception.message}")
            }
        } catch (e: Exception) {
            _error.postValue("Error: ${e.message}")
            Log.e(" genreViewModel", "Error getting quotes: ${e.message}")
        }
    }
}