package com.adewan.scout.ui.features.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.usecases.AreUserPreferencesSetUseCase
import com.adewan.scout.usecases.IsUserLoggedInUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppNavigationViewModel(
    isUserLoggedIn: IsUserLoggedInUseCase,
    areUserPreferencesSetUseCase: AreUserPreferencesSetUseCase
) : ViewModel() {
    var startDestination by mutableStateOf<Any?>(null)
        private set

    init {
        viewModelScope.launch {
            isUserLoggedIn().collectLatest { isUserLoggedIn ->
                if (!isUserLoggedIn) {
                    startDestination = Login
                    return@collectLatest
                }
                areUserPreferencesSetUseCase().collectLatest { areUserPreferencesSet ->
                    if (!areUserPreferencesSet) {
                        startDestination = Preferences
                    } else {
                        startDestination = Main
                    }
                }
            }
        }
    }
}