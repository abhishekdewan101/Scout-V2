package com.adewan.scout.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class IgdbPlatform(val id: Int, val slug: String, val name: String, val generation: Int = -2)