package com.adewan.scout.ui.features.preference

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.adewan.scout.core.models.Platform
import com.adewan.scout.core.platform.PlatformRepository

class PreferenceSelectionViewModel(private val platformRepository: PlatformRepository) :
    ViewModel() {
    val platforms = mutableStateListOf<Platform>()

    fun getPlatforms() {
        platforms.addAll(platformRepository.getPlatforms().sortedByDescending { it.generation })
    }
}