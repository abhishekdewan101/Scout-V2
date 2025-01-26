package com.adewan.scout.utils

import java.text.DecimalFormat

fun Double?.twoDecimalPlaces(): String? {
    if (this == null) return null
    val decimalFormat = DecimalFormat("#.##")
    val formattedValue = decimalFormat.format(this)
    return "$formattedValue%"
}