package com.adewan.scout.ui.features.onboarding

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.features.auth.LoginView
import com.adewan.scout.ui.features.preference.GenreSelectionView
import com.adewan.scout.ui.features.preference.PlatformSelectionView
import com.adewan.scout.ui.features.preference.PreferenceSelectionView

@Composable
fun OnboardingNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06232A)),
        navController = navController,
        startDestination = OnboardingDestinations.LoginView.route
    ) {
        composable(route = OnboardingDestinations.LoginView.route) {
            LoginView(navigateToPreferenceSelection = {
                navController.navigate(
                    OnboardingDestinations.PreferenceSelectionView.route
                ) {
                    popUpTo(route = OnboardingDestinations.LoginView.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = OnboardingDestinations.PreferenceSelectionView.route,
            enterTransition = { fadeIn() },
            popEnterTransition = { fadeIn() },
        ) {
            PreferenceSelectionView(showPlatformSelection = {
                navController.navigate(
                    OnboardingDestinations.PlatformSelectionView.route
                )
            }, showGenreSelection = {
                navController.navigate(OnboardingDestinations.GenreSelectionView.route)
            })
        }

        composable(
            route = OnboardingDestinations.PlatformSelectionView.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }) {
            PlatformSelectionView(goBack = { navController.popBackStack() })
        }

        composable(
            route = OnboardingDestinations.GenreSelectionView.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }) {
            GenreSelectionView(goBack = { navController.popBackStack() })
        }
    }
}