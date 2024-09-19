package com.adewan.scout.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbGame(
    val id: Int,
    val slug: String,
    val rating: Double? = null,
    val name: String,
    @SerialName("first_release_date") val firstReleaseDate: Long? = null,
    @SerialName("cover") val poster: IgdbImage? = null,
    @SerialName("involved_companies") val involvedCompanies: List<IgdbInvolvedCompany> = emptyList(),
    @SerialName("platforms") val platforms: List<IgdbPlatform> = emptyList(),
    val genres: List<IgdbGenre> = emptyList()
)