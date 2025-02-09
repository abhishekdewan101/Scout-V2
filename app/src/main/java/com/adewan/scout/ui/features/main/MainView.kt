package com.adewan.scout.ui.features.main

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.R
import com.adewan.scout.ui.features.home.HomeTabView
import com.adewan.scout.ui.theme.ScoutColors
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavDestination(
    val route: String,
    @DrawableRes val filledIcon: Int,
    @DrawableRes val outlinedIcon: Int
) {
    @Serializable
    data object Home : BottomNavDestination("Home", R.drawable.home_filled, R.drawable.home)

    @Serializable
    data object Library :
        BottomNavDestination("Library", R.drawable.library, R.drawable.library_outlined)

    @Serializable
    data object Profile :
        BottomNavDestination("Profile", R.drawable.profile_filled, R.drawable.profile)

    companion object {
        val all = listOf(Home, Library, Profile)
    }
}

@Composable
fun MainView(
    colors: ScoutColors,
    onColorsChanged: (ScoutColors) -> Unit,
    showSearchView: () -> Unit
) {
    val tabbedNavController = rememberNavController()
    val navBackStackEntry = tabbedNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.backgroundColor,
        bottomBar = {
            MainViewBottomBar(
                currentDestination = currentDestination,
                colors = colors,
                onItemClicked = {
                    tabbedNavController.navigate(it) {
                        popUpTo(BottomNavDestination.Home.route) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = tabbedNavController,
            startDestination = BottomNavDestination.Home.route
        ) {
            composable(BottomNavDestination.Home.route) {
                HomeTabView(
                    colors = colors,
                    onColorsChanged = onColorsChanged,
                    onSearchIconPressed = showSearchView
                )
            }
            composable(BottomNavDestination.Library.route) {
                Text(BottomNavDestination.Library.route)
            }
            composable(BottomNavDestination.Profile.route) {
                Text("Profile")
            }
        }
    }

}

@Composable
fun MainViewBottomBar(
    currentDestination: NavDestination?,
    colors: ScoutColors,
    onItemClicked: (String) -> Unit
) {
    NavigationBar(containerColor = colors.backgroundColor, tonalElevation = 0.dp) {
        BottomNavDestination.all.forEach {
            NavigationBarItem(
                selected = currentDestination?.route == it.route,
                icon = {
                    Icon(
                        painter = painterResource(
                            if (currentDestination?.route == it.route) it.filledIcon else it.outlinedIcon
                        ),
                        contentDescription = it.route,
                        modifier = Modifier.size(24.dp)
                    )
                },
                onClick = { onItemClicked(it.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colors.contrastColor,
                    unselectedIconColor = colors.contrastColor.copy(alpha = 0.5f)
                )
            )
        }
    }
}
