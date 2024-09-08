package com.adewan.scout.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.platform.PlatformRepository
import com.adewan.scout.ui.features.auth.LoginViewModel
import com.adewan.scout.ui.features.preference.PreferenceSelectionViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "scout_preferences")

val appModule = module {
    single { FirebaseAuthenticationRepository(auth = FirebaseAuth.getInstance()) }
    single { PlatformRepository(resources = get<Context>().resources) }
    single<DataStore<Preferences>> { get<Context>().dataStore }

    viewModel { LoginViewModel(authenticationRepository = get()) }
    viewModel { PreferenceSelectionViewModel(platformRepository = get(), dataStore = get()) }
}