package com.adewan.scout.ui.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.components.FullScreenLoadingIndicator
import com.adewan.scout.ui.features.login.LoginView
import com.adewan.scout.ui.features.main.MainView
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(viewModel: AppNavigationViewModel = koinViewModel()) {
    val localStartDestination = viewModel.startDestination
    if (localStartDestination == null) {
        FullScreenLoadingIndicator()
    } else {
        val appNavigationController = rememberNavController()

        NavHost(
            navController = appNavigationController,
            startDestination = localStartDestination!!
        ) {
            composable<Login> {
                LoginView(onLoginSuccessful = {})
            }

            composable<Main> {
                MainView()
            }
        }
    }

}