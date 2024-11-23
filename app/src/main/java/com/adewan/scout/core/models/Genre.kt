package com.adewan.scout.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(val id: Int, val slug: String, val name: String, val url: String)