package com.adewan.scout.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.adewan.scout.core.auth.FirebaseAuthenticationRepository
import com.adewan.scout.core.game.GameRepository
import com.adewan.scout.core.genres.GenreRepository
import com.adewan.scout.core.local.DataStoreRepository
import com.adewan.scout.core.network.NetworkClient
import com.adewan.scout.core.network.QueryGeneratorRepository
import com.adewan.scout.core.platform.PlatformRepository
import com.adewan.scout.ui.features.auth.LoginViewModel
import com.adewan.scout.ui.features.home.HomeTabViewModel
import com.adewan.scout.ui.features.preference.PreferenceSelectionViewModel
import com.adewan.scout.ui.features.profile.ProfileTabViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "scout_preferences")

val appModule = module {
    single {
        FirebaseAuthenticationRepository(
            auth = FirebaseAuth.getInstance(),
            datastore = FirebaseFirestore.getInstance(),
            networkClient = get()
        )
    }
    single { PlatformRepository(resources = get<Context>().resources) }
    single { GenreRepository(resources = get<Context>().resources) }
    single {
        GameRepository(
            networkClient = get(),
            queryGeneratorRepository = get(),
            firebaseAuthenticationRepository = get()
        )
    }
    single {
        QueryGeneratorRepository(
            dataStoreRepository = get(),
            platformRepository = get(),
            genreRepository = get(),
        )
    }
    single { DataStoreRepository(dataStore = get()) }
    single<DataStore<Preferences>> { get<Context>().dataStore }

    viewModel { LoginViewModel(authenticationRepository = get()) }
    viewModel {
        PreferenceSelectionViewModel(
            platformRepository = get(),
            dataStoreRepository = get(),
            genreRepository = get(),
            authenticationRepository = get()
        )
    }
    viewModel { ProfileTabViewModel(authenticationRepository = get()) }
    viewModel { HomeTabViewModel(gameRepository = get()) }

    factory { NetworkClient() }
}