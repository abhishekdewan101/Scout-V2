package com.adewan.scout.usecases

import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.igdb.IgdbRepository
import com.adewan.scout.core.network.models.IgdbGame

class FetchTopRatedGamesUseCase(
    private val authenticationRepository: FirebaseAuthenticationRepository,
    private val gameRepository: IgdbRepository
) {
    suspend operator fun invoke(): List<IgdbGame> {
        authenticationRepository.getAccessToken()
        return gameRepository.getTopRatedGames()
    }
}