package com.adewan.scout.ui.features.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Login

@Serializable
data object Main

@Serializable
data object Search

@Serializable
data object Preferences

@Serializable
data class Details(val slug: String)