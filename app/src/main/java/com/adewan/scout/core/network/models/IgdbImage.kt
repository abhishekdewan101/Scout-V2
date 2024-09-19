package com.adewan.scout.core.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IgdbImage(val id: Int, @SerialName("image_id") val imageId: String) {
    val smallImage = buildImageUrl()
    val largeImage = buildImageUrl("1080p")

    private fun buildImageUrl(imageSize: String = "720p"): String =
        "https://images.igdb.com/igdb/image/upload/t_$imageSize/${imageId}.jpg"
}