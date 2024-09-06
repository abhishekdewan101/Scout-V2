package com.adewan.scout.di

import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.ui.features.auth.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuthenticationRepository(auth = FirebaseAuth.getInstance()) }

    viewModel { LoginViewModel(authenticationRepository = get()) }
}