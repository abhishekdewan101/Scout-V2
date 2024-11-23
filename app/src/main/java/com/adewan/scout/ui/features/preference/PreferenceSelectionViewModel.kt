package com.adewan.scout.ui.features.preference

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.genres.GenreRepository
import com.adewan.scout.core.local.DataStoreRepository
import com.adewan.scout.core.models.Genre
import com.adewan.scout.core.models.Platform
import com.adewan.scout.core.platform.PlatformRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val SELECTED_GENRES_KEY = "selected_genres"

private const val SELECTED_PLATFORMS_KEY = "selected_platforms"

class PreferenceSelectionViewModel(
    private val platformRepository: PlatformRepository,
    private val genreRepository: GenreRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val authenticationRepository: FirebaseAuthenticationRepository,
) : ViewModel() {

    val platforms = mutableStateListOf<Platform>()
    val genres = mutableStateListOf<Genre>()

    fun getPlatforms() {
        platforms.addAll(platformRepository.getPlatforms().sortedByDescending { it.generation })
    }

    fun getGenres() {
        genres.addAll(genreRepository.getGenres().sortedBy { it.name })
    }

    suspend fun getSelectedGenres(): List<Genre> {
        val selectedGenres =
            dataStoreRepository.getStringPreference(SELECTED_GENRES_KEY) ?: return emptyList()
        return genreRepository.getGenresFromString(selectedGenres)
    }

    suspend fun saveSelectedGenres(genres: List<Genre>) {
        dataStoreRepository.setStringPreference(SELECTED_GENRES_KEY, Json.encodeToString(genres))
    }

    suspend fun getSelectedPlatforms(): List<Platform> {
        val selectedPlatforms =
            dataStoreRepository.getStringPreference(SELECTED_PLATFORMS_KEY) ?: return emptyList()
        return platformRepository.getPlatformsFromString(selectedPlatforms)
    }

    suspend fun saveSelectedPlatforms(platforms: List<Platform>) {
        dataStoreRepository.setStringPreference(
            SELECTED_PLATFORMS_KEY,
            Json.encodeToString(platforms)
        )
    }

    suspend fun setOnboardingDone() {
        dataStoreRepository.setBooleanPreference("onboarding_done", true)
    }

    suspend fun isOnboardingDone(): Boolean {
        return dataStoreRepository.getBooleanPreference("onboarding_done", true)
    }

    suspend fun isAccessTokenSet(): Boolean {
        return authenticationRepository.getAccessToken() != null
    }
}