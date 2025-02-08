package com.adewan.scout.ui.features.login

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository

class LoginViewModel(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository
) : ViewModel() {
    fun getCredentialsRequest(): GetCredentialRequest {
        return firebaseAuthenticationRepository.getCredentialRequest()
    }

    fun processGetCredentialResponse(response: GetCredentialResponse) {
        firebaseAuthenticationRepository.processCredential(response)
    }
}