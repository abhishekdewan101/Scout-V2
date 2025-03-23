package com.adewan.scout.utils

import androidx.palette.graphics.Palette
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.defaultScoutColor

fun Palette?.getScoutColors(): PixelColors {
    if (this == null) {
        return defaultScoutColor
    }

    var color = getDominantColor(0)
    if (color == 0) {
        color = getVibrantColor(0)
    }
    return if (color == 0) {
        defaultScoutColor
    } else {
        color.toColor().run {
            PixelColors(backgroundColor = this, contrastColor = this.getContrastingColor())
        }
    }
}
