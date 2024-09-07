package com.adewan.scout.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Platform(val generation: Int = -1, val name: String, val slug: String)