package com.adewan.scout.core.game

import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.network.NetworkClient
import com.adewan.scout.core.network.QueryGeneratorRepository
import com.adewan.scout.core.network.models.IgdbGame

class GameRepository(
    private val networkClient: NetworkClient,
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
    private val queryGeneratorRepository: QueryGeneratorRepository
) {
    suspend fun getUpcomingGames(): List<IgdbGame> {
        val accessToken = firebaseAuthenticationRepository.getCurrentAccessToken()
        return networkClient.getGameInfoRowsForQuery(
            accessToken = accessToken,
            query = queryGeneratorRepository.generateUpcomingQuery()
        )
    }

    suspend fun getTopRatedGames(): List<IgdbGame> {
        val accessToken = firebaseAuthenticationRepository.getCurrentAccessToken()
        return networkClient.getGameInfoRowsForQuery(
            accessToken = accessToken,
            query = queryGeneratorRepository.generateTopRatedQuery()
        )
    }

    suspend fun getRecentlyReleasedGames(): List<IgdbGame> {
        val accessToken = firebaseAuthenticationRepository.getCurrentAccessToken()
        return networkClient.getGameInfoRowsForQuery(
            accessToken = accessToken,
            query = queryGeneratorRepository.generateRecentlyReleasedQuery()
        )
    }

}