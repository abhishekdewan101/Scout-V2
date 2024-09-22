package com.adewan.scout.ui.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.core.game.GameRepository
import com.adewan.scout.core.network.models.IgdbGame
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeTabViewModel(private val gameRepository: GameRepository) : ViewModel() {
    var viewState by mutableStateOf<HomeViewState>(HomeViewState.Loading)
        private set

    var currentListType by mutableStateOf(GameListType.UPCOMING)
        private set

    fun getShowcaseGames() {
        viewModelScope.launch {
            val asyncUpcoming = async { gameRepository.getUpcomingGames() }
            val asyncTopRated = async { gameRepository.getTopRatedGames() }
            val asyncRecentlyReleased = async { gameRepository.getRecentlyReleasedGames() }

            viewState =
                HomeViewState.Success(
                    games = mapOf(
                        GameListType.UPCOMING to asyncUpcoming.await(),
                        GameListType.TOP_RATED to asyncTopRated.await(),
                        GameListType.RECENTLY_RELEASED to asyncRecentlyReleased.await()
                    )
                )
        }
    }

    fun changeListType(gameListType: GameListType) {
        currentListType = gameListType
    }

    fun getGamesByListType(): List<IgdbGame> {
        return (viewState as HomeViewState.Success).games.getOrDefault(currentListType, emptyList())
    }
}

enum class GameListType(val label: String) {
    UPCOMING("Upcoming"),
    TOP_RATED("Top Rated"),
    RECENTLY_RELEASED("Recently Released")
}

sealed interface HomeViewState {
    data object Loading : HomeViewState
    data class Success(val games: Map<GameListType, List<IgdbGame>>) : HomeViewState
}