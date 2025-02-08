package com.adewan.scout.usecases

import com.adewan.scout.core.auth.AuthenticationState
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IsUserLoggedInUseCase(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return firebaseAuthenticationRepository.getAuthenticationState()
            .map { it == AuthenticationState.USER_AUTHENTICATED }
    }
}