package com.adewan.scout.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class IgdbGenre(val id: Int, val slug: String, val name: String)