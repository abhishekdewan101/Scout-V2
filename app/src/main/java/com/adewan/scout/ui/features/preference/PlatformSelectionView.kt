package com.adewan.scout.ui.features.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformSelectionView(goBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {}, navigationIcon = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "",
                modifier = Modifier.clickable { goBack() },
                tint = Color(0xFF9CB5B7)
            )
        }, modifier = Modifier.padding(horizontal = 16.dp))
    }) {
        Text(
            "Platform Selection View",
            modifier = Modifier
                .padding(it)
        )
    }
}