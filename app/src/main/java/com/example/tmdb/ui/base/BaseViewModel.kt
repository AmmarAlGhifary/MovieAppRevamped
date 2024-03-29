package com.example.tmdb.ui.base

import androidx.lifecycle.ViewModel
import com.example.tmdb.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.properties.Delegates

abstract class BaseViewModel : ViewModel() {

    protected var id by Delegates.notNull<Int>()

    protected val successResponse by lazy { mutableListOf<Boolean>() }

    protected var isInitial = false

    protected lateinit var errorText: String

    protected val _uiState = MutableStateFlow(UiState.loadingState())
    val uiState get() = _uiState.asStateFlow()

    protected fun uiState() {
        _uiState.value = if (successResponse.contains(false)) UiState.errorState(
            isInitial,
            errorText
        ) else UiState.successState()
        successResponse.clear()
    }

    fun retryConnection(action: () -> Unit) {
        _uiState.value = UiState.loadingState(isInitial)
        action.invoke()
    }
}