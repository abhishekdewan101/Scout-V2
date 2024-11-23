package com.adewan.scout.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Platform(val id: Int, val generation: Int = -1, val name: String, val slug: String)