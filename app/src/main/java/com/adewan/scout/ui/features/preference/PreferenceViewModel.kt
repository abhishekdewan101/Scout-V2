package com.adewan.scout.ui.features.preference

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adewan.scout.core.igdb.IgdbRepository
import com.adewan.scout.core.network.models.IgdbGenre
import com.adewan.scout.core.network.models.IgdbPlatform
import com.adewan.scout.core.preference.PreferenceRepository
import kotlinx.coroutines.launch

class PreferenceViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val igdbRepository: IgdbRepository
) : ViewModel() {
    var currentSelectedGenres = mutableStateListOf<Int>()
    var currentSelectedPlatforms = mutableStateListOf<Int>()

    var remoteGenres = mutableStateListOf<IgdbGenre>()
    var remotePlatforms = mutableStateListOf<IgdbPlatform>()

    init {
        viewModelScope.launch {
            currentSelectedGenres.addAll(preferenceRepository.getUserPreferredGenres())
            currentSelectedPlatforms.addAll(preferenceRepository.getUserPreferredPlatforms())
        }

        viewModelScope.launch {
            remoteGenres.addAll(igdbRepository.getGenres())
            remotePlatforms.addAll(
                igdbRepository.getPlatforms().sortedByDescending { it.generation })
        }
    }

    fun toggleSelectedGenre(genre: IgdbGenre) {
        if (currentSelectedGenres.contains(genre.id)) {
            currentSelectedGenres.remove(genre.id)
        } else {
            currentSelectedGenres.add(genre.id)
        }
    }

    fun toggleSelectedPlatform(platform: IgdbPlatform) {
        if (currentSelectedPlatforms.contains(platform.id)) {
            currentSelectedPlatforms.remove(platform.id)
        } else {
            currentSelectedPlatforms.add(platform.id)
        }
    }

    fun isGenreSelected(genre: IgdbGenre) = currentSelectedGenres.contains(genre.id)

    fun isPlatformSelected(platform: IgdbPlatform) = currentSelectedPlatforms.contains(platform.id)

    suspend fun setPreferences() {
        preferenceRepository.setUserPreferredGenres(currentSelectedGenres)
        preferenceRepository.setUserPreferredPlatforms(currentSelectedPlatforms)

    }
}