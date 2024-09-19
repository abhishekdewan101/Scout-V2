package com.adewan.scout.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class IgdbCompany(val slug: String, val name: String)