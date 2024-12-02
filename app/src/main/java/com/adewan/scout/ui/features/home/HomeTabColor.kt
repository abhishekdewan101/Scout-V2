package com.adewan.scout.ui.features.home

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class HomeTabColor(val backgroundColor: Color, val contrastColor: Color)

val LocalHomeTabColors = staticCompositionLocalOf {
    HomeTabColor(Color.Transparent, Color.Transparent)
}