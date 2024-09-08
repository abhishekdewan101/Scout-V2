package com.adewan.scout.core.genres

import android.content.res.Resources
import com.adewan.scout.R
import com.adewan.scout.core.models.Genre
import kotlinx.serialization.json.Json

class GenreRepository(private val resources: Resources) {
    private val json = Json { ignoreUnknownKeys = true }

    fun getGenres(): List<Genre> {
        val localJson =
            resources.openRawResource(R.raw.genres).bufferedReader().use { it.readText() }
        return json.decodeFromString(localJson)
    }

    fun getGenresFromString(jsonString: String): List<Genre> {
        return json.decodeFromString(jsonString)
    }
}