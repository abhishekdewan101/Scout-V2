package com.adewan.scout.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class IgdbInvolvedCompany(
    val developer: Boolean,
    val publisher: Boolean,
    val company: IgdbCompany
)