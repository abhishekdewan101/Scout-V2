package com.adewan.scout.ui.features.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.features.auth.LoginView

@Composable
fun OnboardingNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = OnboardingDestinations.LoginView.route
    ) {
        composable(route = OnboardingDestinations.LoginView.route) {
            LoginView()
        }
    }
}