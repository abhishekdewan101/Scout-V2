package com.adewan.scout.core.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun setBooleanPreference(key: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun setStringPreference(key: String, value: String) {
        dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean {
        return dataStore.data.first()[booleanPreferencesKey(key)] ?: defaultValue
    }

    suspend fun getStringPreference(key: String): String? {
        return dataStore.data.first()[stringPreferencesKey(key)]
    }
}