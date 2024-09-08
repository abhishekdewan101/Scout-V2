package com.adewan.scout.ui.features.preference

import androidx.compose.runtime.mutableStateListOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import com.adewan.scout.core.models.Platform
import com.adewan.scout.core.platform.PlatformRepository
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreferenceSelectionViewModel(
    private val platformRepository: PlatformRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val platforms = mutableStateListOf<Platform>()

    fun getPlatforms() {
        platforms.addAll(platformRepository.getPlatforms().sortedByDescending { it.generation })
    }

    suspend fun getSelectedPlatforms(): List<Platform> {
        val data = dataStore.data.first()
        val key = stringPreferencesKey("selected_platforms")
        val existingSelectedPlatforms = data.toPreferences()[key] ?: return emptyList()
        return platformRepository.getPlatformsFromString(existingSelectedPlatforms)
    }

    suspend fun saveSelectedPlatforms(platforms: List<Platform>) {
        dataStore.edit {
            it[stringPreferencesKey("selected_platforms")] = Json.encodeToString(platforms)
        }
    }
}