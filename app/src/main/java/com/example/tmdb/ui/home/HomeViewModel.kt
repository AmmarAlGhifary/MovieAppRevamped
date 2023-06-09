package com.example.tmdb.ui.home

import com.example.tmdb.domain.usecase.GetSearchResult
import com.example.tmdb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getSearchResult: GetSearchResult) : BaseViewModel() {

}