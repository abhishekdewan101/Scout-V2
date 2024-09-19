package com.adewan.scout.ui.features.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(viewModel: HomeTabViewModel = koinViewModel()) {
    LaunchedEffect(viewModel) { viewModel.getUpcomingGames() }

    Text("Home Tab View")
}