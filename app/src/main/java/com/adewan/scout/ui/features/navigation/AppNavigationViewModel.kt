package com.adewan.scout.ui.features.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.usecases.IsUserLoggedInUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppNavigationViewModel(isUserLoggedIn: IsUserLoggedInUseCase) : ViewModel() {
    var startDestination by mutableStateOf<Any?>(null)
        private set

    init {
        viewModelScope.launch {
            isUserLoggedIn().map { if (it) Main else Login }.collect {
                startDestination = it
            }
        }
    }
}