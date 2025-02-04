package com.adewan.scout.ui.features.profile

import androidx.lifecycle.ViewModel
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository

class ProfileTabViewModel(private val authenticationRepository: FirebaseAuthenticationRepository) :
    ViewModel() {

    fun logout() {
        authenticationRepository.logout()
    }

    fun getInitials(): String {
        val name = authenticationRepository.getCurrentUser().displayName?.split(" ") ?: return "SU"
        return name[0].first().uppercase() + name[1].first().uppercase()
    }
}