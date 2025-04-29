package com.adewan.scout.ui.features.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.core.auth.AuthenticationState
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.preference.PreferenceRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppNavigationViewModel(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    var startDestination by mutableStateOf<Any?>(null)
        private set

    init {
        viewModelScope.launch {
            firebaseAuthenticationRepository.getAuthenticationState().collectLatest { authState ->
                val isUserLoggedIn = authState == AuthenticationState.USER_AUTHENTICATED

                if (!isUserLoggedIn) {
                    startDestination = Login
                    return@collectLatest
                }

                val arePreferencesSet =
                    preferenceRepository.getUserPreferredGenres().isNotEmpty() &&
                            preferenceRepository.getUserPreferredPlatforms().isNotEmpty()

                if (!arePreferencesSet) {
                    startDestination = Preferences
                } else {
                    startDestination = Main
                }
            }
        }
    }
}