package com.adewan.scout.ui.features.auth

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository

class LoginViewModel(
    private val authenticationRepository: FirebaseAuthenticationRepository
) : ViewModel() {
    val authenticationState = authenticationRepository.getAuthenticationState()

    fun getCredentialRequest() = authenticationRepository.getCredentialRequest()

    fun processCredentials(response: GetCredentialResponse) =
        authenticationRepository.processCredential(response)

    suspend fun getAccessToken(): String? {
        return authenticationRepository.getAccessToken()
    }
}