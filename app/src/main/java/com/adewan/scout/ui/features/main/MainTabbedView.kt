package com.adewan.scout.ui.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.ui.features.home.HomeTabColor
import com.adewan.scout.ui.features.home.HomeTabView
import com.adewan.scout.ui.features.home.LocalHomeTabColors
import com.adewan.scout.utils.getContrastingColor

@Composable
fun MainTabbedView() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var currentBackgroundColor by remember { mutableStateOf(Color(0xFF1D1D1D)) }

    CompositionLocalProvider(
        LocalHomeTabColors provides HomeTabColor(
            backgroundColor = currentBackgroundColor,
            contrastColor = currentBackgroundColor.getContrastingColor()
        )
    ) {
        val homeTabColor = LocalHomeTabColors.current
        Scaffold(modifier = Modifier
            .fillMaxSize(), containerColor = currentBackgroundColor, bottomBar = {
            NavigationBar(tonalElevation = 0.dp, containerColor = Color.Transparent) {
                TabDestinations.entries.forEach { destination ->
                    NavigationBarItem(selected = false, icon = {
                        if (currentDestination?.route == destination.route) {
                            Icon(
                                painter = painterResource(destination.selectedIcon),
                                "",
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(destination.icon),
                                "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }, onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = homeTabColor.contrastColor,
                            unselectedIconColor = homeTabColor.contrastColor.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }) {

            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = TabDestinations.Home.route
            ) {
                composable(route = TabDestinations.Home.route) {
                    HomeTabView(onImageShown = { color ->
                        currentBackgroundColor = color
                    })
                }
                composable(route = TabDestinations.Library.route) {
                    Text("Library View")
                }
                composable(route = TabDestinations.Profile.route) {
                    Text("Profile View")
                }
            }
        }
    }
}