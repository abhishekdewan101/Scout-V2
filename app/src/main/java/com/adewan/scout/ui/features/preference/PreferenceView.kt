package com.adewan.scout.ui.features.preference

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adewan.scout.R
import com.adewan.scout.ui.components.FullScreenLoadingIndicator
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.defaultPixelColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.fetchPixelColorsSideEffect
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object PreferenceMain

@Serializable
data object PreferenceGenres

@Serializable
data object PreferencePlatform


@Composable
fun PreferenceNavHost(
    viewModel: PreferenceViewModel = koinViewModel(),
    navigateToMain: () -> Unit
) {
    val navController = rememberNavController()
    var colors by remember { mutableStateOf(defaultPixelColors) }
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = PreferenceMain) {
        composable<PreferenceMain>(
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    targetOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    initialOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            }
        ) {
            PreferenceView(
                areGenresSelected = viewModel.currentSelectedGenres.isNotEmpty(),
                arePlatformsSelected = viewModel.currentSelectedPlatforms.isNotEmpty(),
                onColorsAvailable = { colors = it },
                navigateToGenreSelection = { navController.navigate(PreferenceGenres) },
                navigateToPreferenceSelection = { navController.navigate(PreferencePlatform) },
                onPreferencesSet = {
                    scope.launch {
                        viewModel.setPreferences()
                        navigateToMain()
                    }
                }
            )
        }

        composable<PreferencePlatform>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(150))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    targetOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    initialOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(150))
            }
        ) {
            PreferenceSelectionList(
                colors = colors,
                title = "Select your preferred platform",
                onBackPressed = { navController.popBackStack() },
                getLabel = { it.name },
                isSelected = { viewModel.isPlatformSelected(it) },
                onSelected = viewModel::toggleSelectedPlatform,
                preferences = viewModel.remotePlatforms
            )
        }

        composable<PreferenceGenres>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(150))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    targetOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    initialOffset = { it / 3 },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(150))
            }
        ) {
            PreferenceSelectionList(
                colors = colors,
                title = "Select your preferred genres",
                onBackPressed = { navController.popBackStack() },
                getLabel = { it.name },
                isSelected = { viewModel.isGenreSelected(it) },
                onSelected = viewModel::toggleSelectedGenre,
                preferences = viewModel.remoteGenres
            )
        }
    }
}


@Composable
fun PreferenceView(
    areGenresSelected: Boolean,
    arePlatformsSelected: Boolean,
    onColorsAvailable: (PixelColors) -> Unit,
    navigateToGenreSelection: () -> Unit,
    navigateToPreferenceSelection: () -> Unit,
    onPreferencesSet: () -> Unit,
) {
    val colors = fetchPixelColorsSideEffect(imageSource = R.drawable.preferencebackground)

    LaunchedEffect(colors) {
        onColorsAvailable(colors)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(24.dp)
    ) {
        Image(
            painterResource(R.drawable.preferencebackground),
            "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterStart,
            modifier = Modifier.weight(0.6f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = spacedBy(24.dp)
        ) {
            Text(
                "Tell us about your gaming preferences", style = TextStyle(
                    fontSize = 18.sp,
                    color = colors.contrastColor,
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.Bold
                )
            )
            PreferenceNavigationLink(
                colors = colors,
                "Select Genres",
                navigate = navigateToGenreSelection,
                selected = areGenresSelected
            )

            PreferenceNavigationLink(
                colors = colors,
                "Select Platforms",
                navigate = navigateToPreferenceSelection,
                selected = arePlatformsSelected
            )


            FilledTonalButton(
                onClick = onPreferencesSet,
                enabled = areGenresSelected && arePlatformsSelected,
                colors = ButtonDefaults.filledTonalButtonColors(
                    contentColor = colors.backgroundColor, containerColor = colors.contrastColor
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Done",
                    color = colors.backgroundColor,
                    fontSize = 16.sp,
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun PreferenceNavigationLink(
    colors: PixelColors,
    title: String,
    navigate: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(colors.contrastColor, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = navigate)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = colors.backgroundColor,
            fontSize = 16.sp,
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (selected) Icons.Default.Check else Icons.Default.ChevronRight,
            contentDescription = null,
            tint = colors.backgroundColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PreferenceSelectionList(
    colors: PixelColors,
    title: String,
    onBackPressed: () -> Unit,
    getLabel: (T) -> String,
    isSelected: (T) -> Boolean,
    onSelected: (T) -> Unit,
    preferences: List<T>
) {
    if (preferences.isEmpty()) {
        FullScreenLoadingIndicator()
    } else {
        var searchQuery by remember { mutableStateOf("") }
        val filteredPreferences = remember(searchQuery, preferences) {
            if (searchQuery.isEmpty()) {
                preferences
            } else {
                preferences.filter {
                    getLabel(it).contains(searchQuery, ignoreCase = true)
                }
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            color = colors.contrastColor,
                            fontSize = 16.sp,
                            fontFamily = poppinsFont,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(Icons.Default.ArrowBack, "", tint = colors.contrastColor)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colors.backgroundColor,
                        titleContentColor = colors.contrastColor,
                        navigationIconContentColor = colors.contrastColor
                    )
                )
            },
            containerColor = colors.backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.backgroundColor)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = {
                        Text(
                            "Search",
                            color = colors.contrastColor.copy(alpha = 0.6f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = colors.contrastColor
                        )
                    },
                    textStyle = TextStyle(color = colors.contrastColor),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.contrastColor,
                        unfocusedBorderColor = colors.contrastColor.copy(alpha = 0.6f),
                        cursorColor = colors.contrastColor,
                        focusedContainerColor = colors.backgroundColor,
                        unfocusedContainerColor = colors.backgroundColor,
                        focusedTextColor = colors.contrastColor,
                        unfocusedTextColor = colors.contrastColor
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(colors.contrastColor)
                ) {
                    itemsIndexed(filteredPreferences) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelected(item)
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = getLabel(item),
                                color = colors.backgroundColor,
                                fontSize = 16.sp,
                                fontFamily = poppinsFont,
                                fontWeight = FontWeight.SemiBold
                            )

                            if (isSelected(item)) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = colors.backgroundColor
                                )
                            }
                        }
                        if (index != filteredPreferences.lastIndex) {
                            HorizontalDivider(color = colors.backgroundColor)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreferenceViewPreview() {
    PreferenceNavHost(navigateToMain = {})
}