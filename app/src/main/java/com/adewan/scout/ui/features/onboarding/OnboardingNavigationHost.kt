package com.adewan.scout.ui.features.onboarding

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.features.auth.LoginView
import com.adewan.scout.ui.features.main.MainNavigationHost
import com.adewan.scout.ui.features.preference.GenreSelectionView
import com.adewan.scout.ui.features.preference.PlatformSelectionView
import com.adewan.scout.ui.features.preference.PreferenceSelectionView
import com.adewan.scout.ui.features.preference.PreferenceSelectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingNavigationHost(viewModel: PreferenceSelectionViewModel = koinViewModel()) {
    val navController = rememberNavController()

    var startDestination by remember { mutableStateOf(OnboardingDestinations.LoginView.route) }
    var startDestinationSet by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        if (viewModel.isOnboardingDone()) {
            startDestination = OnboardingDestinations.MainNavigationView.route
        }
        startDestinationSet = true
    }

    if (startDestinationSet) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF06232A)),
            navController = navController,
            startDestination = startDestination
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
                }, showMainNavigation = {
                    navController.navigate(OnboardingDestinations.MainNavigationView.route) {
                        popUpTo(route = OnboardingDestinations.PreferenceSelectionView.route) {
                            inclusive = true
                        }
                    }
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

            composable(
                route = OnboardingDestinations.MainNavigationView.route,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }) {
                MainNavigationHost()
            }
        }
    }
}