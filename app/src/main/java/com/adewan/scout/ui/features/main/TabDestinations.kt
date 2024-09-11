package com.adewan.scout.ui.features.main

import com.adewan.scout.R

enum class TabDestinations(val route: String, val icon: Int, val selectedIcon: Int) {
    Home("home", R.drawable.home, R.drawable.home_filled),
    Library("library", R.drawable.library_outlined, R.drawable.library),
    Profile("profile", R.drawable.profile, R.drawable.profile_filled)
}