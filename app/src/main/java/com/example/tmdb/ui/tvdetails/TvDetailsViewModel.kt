package com.example.tmdb.ui.tvdetails

import com.example.tmdb.domain.model.TvDetail
import com.example.tmdb.domain.usecase.GetDetails
import com.example.tmdb.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
) : BaseViewModel() {

    private val _details = MutableStateFlow(TvDetail.empty)
    val details get() = _details.asStateFlow()

}