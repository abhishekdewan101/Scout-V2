package com.adewan.scout.ui.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.features.login.LoginView

@Composable
fun AppNavigation() {
    val appNavigationController = rememberNavController()
    NavHost(
        navController = appNavigationController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginView()
        }
    }
}