package com.adewan.scout.ui.features.search

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
fun SearchView(colors: ScoutColors) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search View", color = colors.contrastColor)
    }
}