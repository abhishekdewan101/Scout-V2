package com.adewan.scout.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.core.game.GameRepository
import kotlinx.coroutines.launch

class HomeTabViewModel(private val gameRepository: GameRepository) : ViewModel() {
    fun getUpcomingGames() {
        viewModelScope.launch {
            val games = gameRepository.getUpcomingGames()
        }
    }
}