package com.adewan.scout.usecases

import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.igdb.IgdbRepository
import com.adewan.scout.core.network.models.IgdbGame
import com.adewan.scout.ui.features.home.ShowcaseListType

class FetchGamesForShowcaseList(
    private val authenticationRepository: FirebaseAuthenticationRepository,
    private val gameRepository: IgdbRepository
) {
    suspend operator fun invoke(type: ShowcaseListType): List<IgdbGame> {
        authenticationRepository.getAccessToken()
        return when (type) {
            ShowcaseListType.Upcoming -> gameRepository.getUpcomingGames()
            ShowcaseListType.Recent -> gameRepository.getRecentlyReleasedGames()
        }
    }
}