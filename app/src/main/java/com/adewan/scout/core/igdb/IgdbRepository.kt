package com.adewan.scout.core.igdb

import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.network.NetworkClient
import com.adewan.scout.core.network.QueryGeneratorRepository
import com.adewan.scout.core.network.models.IgdbGame
import com.adewan.scout.core.network.models.IgdbGenre
import com.adewan.scout.core.network.models.IgdbPlatform

class IgdbRepository(
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

    suspend fun getGenres(): List<IgdbGenre> {
        val accessToken = firebaseAuthenticationRepository.getCurrentAccessToken()
        return networkClient.getGenres(accessToken = accessToken)
    }

    suspend fun getPlatforms(): List<IgdbPlatform> {
        val accessToken = firebaseAuthenticationRepository.getCurrentAccessToken()
        return networkClient.getPlatforms(accessToken = accessToken)
    }
}