package com.example.tmdb.ui.authentication

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.Session
import com.example.tmdb.domain.usecase.CreateSession
import com.example.tmdb.domain.usecase.GetRequestToken
import com.example.tmdb.ui.base.BaseViewModel
import com.example.tmdb.util.Resource
import com.example.tmdb.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val getRequestToken: GetRequestToken,
    private val createSession: CreateSession,
    private val dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _session = MutableLiveData<Session?>()
    val session: LiveData<Session?> get() = _session

    private val _authUiState = MutableLiveData(UiState.loadingState())
    val authUiState: LiveData<UiState> get() = _authUiState

    private val _requestToken = MutableLiveData<String>()
    val requestToken: LiveData<String> get() = _requestToken

    fun fetchRequestToken(redirectTo: String) {
        viewModelScope.launch {
            when (val result = getRequestToken.execute(redirectTo)) {
                is Resource.Success -> {
                    Log.d(this@AuthenticationViewModel.toString(), "Request token created: ${result.data.requestToken}")
                    _requestToken.value = result.data.requestToken
                }
                is Resource.Error -> {
                    Log.e(this@AuthenticationViewModel.toString(), "Failed to fetch request token: ${result.message}")
                    _authUiState.value = UiState.errorState(errorText = result.message ?: "Unknown error")
                }
            }
        }
    }

    fun authenticate(requestToken: String) {
        Log.d("AuthViewModel", "Authenticating with request token: $requestToken")
        _authUiState.value = UiState.loadingState()
        viewModelScope.launch {
            when (val result = createSession.execute(requestToken)) {
                is Resource.Success -> {
                    Log.d("AuthViewModel", "Session created: ${result.data.sessionId}")
                    _session.value = result.data
                    saveSession(result.data)
                    _authUiState.value = UiState.successState()
                }
                is Resource.Error -> {
                    Log.e("AuthViewModel", "Failed to create session: ${result.message}")
                    _authUiState.value = UiState.errorState(errorText = result.message ?: "Unknown error")
                }
            }
        }
    }

    fun isAuthenticated(): Boolean {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[PreferencesKeys.SESSION_ID]
            }.first() != null
        }
    }

    private fun saveSession(session: Session) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.SESSION_ID] = session.sessionId
            }
        }
    }

    private object PreferencesKeys {
        val SESSION_ID = stringPreferencesKey("session_id")
    }
}
