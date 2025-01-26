package com.adewan.scout.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long?.buildReleaseDateString(): String {
    if (this == null) return "NA"
    val instant = Instant.ofEpochSecond(this)
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault())
    val formattedDate = formatter.format(instant)
    return formattedDate
}

fun Long?.buildReleaseYearString(): String {
    if (this == null) return "NA"
    val instant = Instant.ofEpochSecond(this)
    val formatter = DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault())
    val formattedDate = formatter.format(instant)
    return formattedDate
}