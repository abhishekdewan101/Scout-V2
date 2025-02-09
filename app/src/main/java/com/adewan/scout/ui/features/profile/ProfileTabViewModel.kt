package com.adewan.scout.ui.features.profile

import androidx.lifecycle.ViewModel
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository

class ProfileTabViewModel(private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository) :
    ViewModel() {
    fun logoutUser() {
        firebaseAuthenticationRepository.logout()
    }
}