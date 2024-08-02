package com.example.tmdb.ui.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.model.Profile
import com.example.tmdb.domain.usecase.GetProfile
import com.example.tmdb.ui.base.BaseViewModel
import com.example.tmdb.util.Resource
import com.example.tmdb.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfile: GetProfile,
    private val dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _avatarUrl = MutableStateFlow("")
    val avatarUrl get() = _avatarUrl.asStateFlow()

    private val _name = MutableStateFlow("")
    val name get() = _name.asStateFlow()

    private val _username = MutableStateFlow("")
    val username get() = _username.asStateFlow()

    private val _includeAdult = MutableStateFlow(false)
    val includeAdult get() = _includeAdult.asStateFlow()

    private val _language = MutableStateFlow("")
    val language get() = _language.asStateFlow()

    private val _country = MutableStateFlow("")
    val country get() = _country.asStateFlow()

    private val _profileUiState = MutableStateFlow(UiState.loadingState())
    val profileUiState get() = _profileUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val sessionId = dataStore.data.map { preferences ->
                preferences[PreferencesKeys.SESSION_ID]
            }.first()
            sessionId?.let { fetchProfile(it) }
        }
    }

    fun fetchProfile(sessionId: String) {
        _profileUiState.value = UiState.loadingState()
        viewModelScope.launch {
            val result = getProfile.invoke(sessionId)
            _profileUiState.value = when (result) {
                is Resource.Success -> {
                    setProfileData(result.data)
                    UiState.successState()
                }

                is Resource.Error -> UiState.errorState(errorText = result.message)
            }
        }
    }

    private fun setProfileData(data: Profile) {
        _avatarUrl.value = data.avatarUrl
        _name.value = data.name
        _username.value = data.username
        _includeAdult.value = data.includeAdult
        _language.value = data.language
        _country.value = data.country
    }

    private object PreferencesKeys {
        val SESSION_ID = stringPreferencesKey("session_id")
    }
}
