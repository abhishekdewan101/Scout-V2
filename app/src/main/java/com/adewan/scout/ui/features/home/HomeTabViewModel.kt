package com.adewan.scout.ui.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.core.igdb.IgdbRepository
import com.adewan.scout.core.network.models.IgdbGame
import kotlinx.coroutines.launch

enum class ShowcaseListType(val title: String) {
    Upcoming("Upcoming"),
    Recent("Recently Released")
}

class HomeTabViewModel(
    private val igdbRepository: IgdbRepository
) :
    ViewModel() {
    var currentSelectedList by mutableStateOf(ShowcaseListType.Upcoming)
        private set

    var currentShowcaseGames = mutableStateListOf<IgdbGame>()
        private set

    var currentTopRatedGames = mutableStateListOf<IgdbGame>()
        private set

    init {
        viewModelScope.launch {
            currentShowcaseGames.addAll(getShowcaseGames(currentSelectedList))
        }
        viewModelScope.launch {
            currentTopRatedGames.addAll(igdbRepository.getTopRatedGames())
        }
    }

    fun updateCurrentSelectedList(listType: ShowcaseListType) {
        currentSelectedList = listType
        viewModelScope.launch {
            currentShowcaseGames.clear()
            currentShowcaseGames.addAll(getShowcaseGames(currentSelectedList))
        }
    }

    private suspend fun getShowcaseGames(type: ShowcaseListType): List<IgdbGame> {
        return when (type) {
            ShowcaseListType.Upcoming -> igdbRepository.getUpcomingGames()
            ShowcaseListType.Recent -> igdbRepository.getRecentlyReleasedGames()
        }
    }
}