package com.adewan.scout.utils

import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.adewan.scout.ui.theme.PixelColors

@Composable
fun ExtractColorFromImage(
    currentIndex: Int,
    items: List<String>,
    onColorsChanged: (PixelColors) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(currentIndex) {
        val imageLoader = ImageLoader.Builder(context).build()
        val imageRequest = ImageRequest.Builder(context)
            .size(250, 350)
            .data(items[currentIndex])
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .allowHardware(false)
            .build()
        val result = imageLoader.execute(imageRequest)
        val bitmap = if (result is SuccessResult) {
            (result.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
        if (bitmap != null) {
            Palette.from(bitmap).generate {
                onColorsChanged(it.toPixelColors())
            }
        }
    }
}