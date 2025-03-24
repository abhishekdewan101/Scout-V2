package com.adewan.scout.ui.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.adewan.scout.ui.components.FullScreenLoadingIndicator
import com.adewan.scout.ui.features.details.DetailsView
import com.adewan.scout.ui.features.login.LoginView
import com.adewan.scout.ui.features.main.MainView
import com.adewan.scout.ui.features.preference.PreferenceNavHost
import com.adewan.scout.ui.features.search.SearchView
import com.adewan.scout.ui.theme.defaultPixelColors
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(viewModel: AppNavigationViewModel = koinViewModel()) {
    val localStartDestination = viewModel.startDestination
    if (localStartDestination == null) {
        FullScreenLoadingIndicator()
    } else {
        val appNavigationController = rememberNavController()
        var currentPixelColors by remember { mutableStateOf(defaultPixelColors) }

        NavHost(
            navController = appNavigationController, startDestination = localStartDestination
        ) {
            composable<Login> {
                LoginView()
            }

            composable<Preferences> {
                PreferenceNavHost()
            }

            composable<Main> {
                MainView(
                    colors = currentPixelColors,
                    onColorsChanged = { currentPixelColors = it },
                    showSearchView = {
                        appNavigationController.navigate(Search)
                    },
                    showDetailsView = {
                        appNavigationController.navigate(Details(slug = it))
                    })
            }

            composable<Search>(
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            ) {
                SearchView(colors = currentPixelColors, navigateToDetailView = {
                    appNavigationController.navigate(Details(slug = it))
                })
            }

            composable<Details>(
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            ) { backStackEntry ->
                val details: Details = backStackEntry.toRoute()
                DetailsView(
                    colors = currentPixelColors,
                    slug = details.slug,
                    navigateToDetailView = {
                        appNavigationController.navigate(Details(slug = it))
                    },
                    navigateToSearchView = {
                        appNavigationController.navigate(Search)
                    },
                    navigateBack = {
                        appNavigationController.popBackStack()
                    })
            }
        }
    }

}