package com.adewan.scout.usecases

import com.adewan.scout.core.preference.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AreUserPreferencesSetUseCase(private val preferenceRepository: PreferenceRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return flow {
            emit(
                preferenceRepository.getUserPreferredGenres()
                    .isNotEmpty() && preferenceRepository.getUserPreferredPlatforms().isNotEmpty()
            )
        }
    }
}