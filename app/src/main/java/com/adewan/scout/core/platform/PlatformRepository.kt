package com.adewan.scout.core.platform

import android.content.res.Resources
import com.adewan.scout.R
import com.adewan.scout.core.models.Platform
import kotlinx.serialization.json.Json

class PlatformRepository(private val resources: Resources) {
    fun getPlatforms(): List<Platform> {
        val localJson =
            resources.openRawResource(R.raw.platforms).bufferedReader().use { it.readText() }
        return Json { ignoreUnknownKeys = true }.decodeFromString(localJson)
    }
}