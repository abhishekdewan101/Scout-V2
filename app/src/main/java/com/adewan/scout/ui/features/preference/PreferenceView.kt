package com.adewan.scout.ui.features.preference

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.R
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.poppinsFont

@Composable
fun PreferenceView(colors: PixelColors) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(24.dp)
    ) {
        Image(
            painterResource(R.drawable.preferencebackground),
            "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterStart,
            modifier = Modifier.height(600.dp)
        )
        Text(
            "Tell us about your gaming preferences", style = TextStyle(
                fontSize = 18.sp,
                color = colors.contrastColor,
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .clickable(onClick = {})
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Select Genres",
                color = colors.backgroundColor,
                fontSize = 16.sp,
                fontFamily = poppinsFont,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (false) Icons.Default.ChevronRight else Icons.Default.Check,
                contentDescription = null,
                tint = colors.backgroundColor
            )
        }
    }
}