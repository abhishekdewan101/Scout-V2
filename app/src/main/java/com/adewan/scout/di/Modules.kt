package com.adewan.scout.di

import android.content.Context
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.platform.PlatformRepository
import com.adewan.scout.ui.features.auth.LoginViewModel
import com.adewan.scout.ui.features.preference.PreferenceSelectionViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuthenticationRepository(auth = FirebaseAuth.getInstance()) }
    single { PlatformRepository(resources = get<Context>().resources) }

    viewModel { LoginViewModel(authenticationRepository = get()) }
    viewModel { PreferenceSelectionViewModel(platformRepository = get()) }
}