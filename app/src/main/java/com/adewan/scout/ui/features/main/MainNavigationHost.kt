package com.adewan.scout.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D1D1D)),
        navController = navController,
        startDestination = MainDestinations.HomeView.route
    ) {
        composable(route = MainDestinations.HomeView.route) {
            Text("Home View")
        }
    }
}