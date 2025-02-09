package com.adewan.scout.ui.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adewan.scout.ui.theme.ScoutColors

@Composable
fun DetailsView(colors: ScoutColors, slug: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details View For $slug", color = colors.contrastColor)
    }
}