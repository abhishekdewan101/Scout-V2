package com.adewan.scout.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.getContrastingColor(): Color {
    // Calculate the luminance of the color
    val luminance = this.luminance()

    // Return white for dark colors and black for light colors
    return if (luminance > 0.5) Color.Black else Color.White
}