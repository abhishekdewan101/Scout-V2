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

    var currentListTypeGames by mutableStateOf<List<IgdbGame>>(emptyList())

    fun getShowcaseGames() {
        viewModelScope.launch {
            val asyncUpcoming = async { gameRepository.getUpcomingGames() }
            val asyncRecentlyReleased = async { gameRepository.getRecentlyReleasedGames() }
            val asyncAllTimeBest = async { gameRepository.getTopRatedGames() }

            viewState =
                HomeViewState.Success(
                    games = mapOf(
                        GameListType.UPCOMING to asyncUpcoming.await(),
                        GameListType.RECENTLY_RELEASED to asyncRecentlyReleased.await()
                    ),
                    allTimeBestGames = asyncAllTimeBest.await()
                )

            currentListTypeGames = getGamesByListType(currentListType)
        }
    }

    fun changeListType(gameListType: GameListType) {
        currentListType = gameListType
        currentListTypeGames = getGamesByListType(gameListType)
    }

    private fun getGamesByListType(listType: GameListType): List<IgdbGame> {
        return (viewState as HomeViewState.Success).games.getOrDefault(listType, emptyList())
    }
}

enum class GameListType(val label: String) {
    UPCOMING("Upcoming"),
    RECENTLY_RELEASED("Recently Released")
}

sealed interface HomeViewState {
    data object Loading : HomeViewState
    data class Success(
        val games: Map<GameListType, List<IgdbGame>>,
        val allTimeBestGames: List<IgdbGame>
    ) : HomeViewState
}