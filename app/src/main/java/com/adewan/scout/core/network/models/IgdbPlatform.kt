package com.adewan.scout.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class IgdbPlatform(val slug: String, val name: String)