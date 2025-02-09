package com.adewan.scout.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adewan.scout.ui.theme.ScoutTheme
import com.adewan.scout.ui.theme.poppinsFont

val imagePlaceHolder by lazy {
    BrushPainter(
        Brush.linearGradient(
            listOf(
                Color(color = 0xFF2196F3),
                Color(color = 0xFFE91E63),
            )
        )
    )
}

@Composable
fun SpacerPoster(width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(Color.Transparent)
    )
}

@Composable
fun GamePoster(
    modifier: Modifier = Modifier,
    posterUrl: String,
    width: Dp,
    height: Dp,
    accessoryText: String? = null,
) {
    Box(modifier) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current).data(posterUrl).crossfade(true).build(),
            contentDescription = "",
            placeholder = imagePlaceHolder,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .width(width = width)
                    .height(height = height)
                    .clip(MaterialTheme.shapes.medium)

        )

        if (accessoryText != null) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.75f))
            ) {
                Text(
                    accessoryText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFont,
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGamePoster() {
    ScoutTheme {
        Column {
            GamePoster(
                posterUrl = "",
                width = 200.dp,
                height = 300.dp,
                accessoryText = "July 17, 2020",
            )
            Spacer(modifier = Modifier.height(70.dp))
            GamePoster(
                posterUrl = "",
                width = 200.dp,
                height = 300.dp,
                accessoryText = "97.5%",
            )
        }
    }
}
