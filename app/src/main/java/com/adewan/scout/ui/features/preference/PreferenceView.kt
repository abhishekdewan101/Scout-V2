package com.adewan.scout.ui.features.preference

import androidx.compose.animation.AnimatedContentTransitionScope
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.adewan.scout.core.network.models.IgdbGenre
import com.adewan.scout.core.network.models.IgdbPlatform
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.defaultPixelColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.fetchPixelColorsSideEffect
import kotlinx.serialization.Serializable

@Serializable
data object PreferenceMain

@Serializable
data object PreferenceGenres

@Serializable
data object PreferencePlatform


@Composable
fun PreferenceNavHost() {
    val navController = rememberNavController()
    var colors by remember { mutableStateOf(defaultPixelColors) }

    var currentSelectedPlatforms = remember { mutableStateListOf<IgdbPlatform>() }
    var currentSelectedGenres = remember { mutableStateListOf<IgdbGenre>() }

    NavHost(navController = navController, startDestination = PreferenceMain) {
        composable<PreferenceMain> {
            PreferenceView(
                onColorsAvailable = { colors = it },
                navigateToGenreSelection = { navController.navigate(PreferenceGenres) },
                navigateToPreferenceSelection = { navController.navigate(PreferencePlatform) })
        }

        composable<PreferencePlatform>(enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, initialOffset = { it + 200 })
        }) {
            PreferenceSelectionList(
                colors = colors,
                title = "Select your preferred platform",
                onBackPressed = { navController.popBackStack() },
                getLabel = { it.name },
                isSelected = { currentSelectedPlatforms.contains(it) },
                onSelected = {
                    if (currentSelectedPlatforms.contains(it)) {
                        currentSelectedPlatforms.remove(it)
                    } else {
                        currentSelectedPlatforms.add(it)
                    }
                },
                preferences = listOf(
                    IgdbPlatform("slug1", "Platform 1"),
                    IgdbPlatform("slug2", "Platform 2"),
                    IgdbPlatform("slug3", "Platform 3"),
                    IgdbPlatform("slug4", "Platform 4"),
                    IgdbPlatform("slug5", "Platform 5"),
                    IgdbPlatform("slug6", "Platform 6"),
                    IgdbPlatform("slug7", "Platform 7"),
                    IgdbPlatform("slug8", "Platform 8"),
                    IgdbPlatform("slug9", "Platform 9"),
                    IgdbPlatform("slug10", "Platform 10"),
                    IgdbPlatform("slug11", "Platform 11"),
                    IgdbPlatform("slug12", "Platform 12"),
                    IgdbPlatform("slug13", "Platform 13"),
                    IgdbPlatform("slug14", "Platform 14"),
                    IgdbPlatform("slug15", "Platform 15"),
                    IgdbPlatform("slug16", "Platform 16"),
                    IgdbPlatform("slug17", "Platform 17"),
                    IgdbPlatform("slug18", "Platform 18"),
                )
            )
        }

        composable<PreferenceGenres>(enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, initialOffset = { it + 200 })
        }) {

            PreferenceSelectionList(
                colors = colors,
                title = "Select your preferred genres",
                onBackPressed = { navController.popBackStack() },
                getLabel = { it.name },
                isSelected = { currentSelectedGenres.contains(it) },
                onSelected = {
                    if (currentSelectedGenres.contains(it)) {
                        currentSelectedGenres.remove(it)
                    } else {
                        currentSelectedGenres.add(it)
                    }
                },
                preferences = listOf(
                    IgdbGenre("slug1", "Genre1"),
                    IgdbGenre("slug2", "Genre2"),
                    IgdbGenre("slug3", "Genre3"),
                    IgdbGenre("slug4", "Genre4"),
                    IgdbGenre("slug5", "Genre5"),
                    IgdbGenre("slug6", "Genre6"),
                    IgdbGenre("slug7", "Genre7"),
                    IgdbGenre("slug8", "Genre8"),
                    IgdbGenre("slug9", "Genre9"),
                    IgdbGenre("slug10", "Genre10"),
                    IgdbGenre("slug11", "Genre11"),
                    IgdbGenre("slug12", "Genre12"),
                    IgdbGenre("slug13", "Genre13"),
                    IgdbGenre("slug14", "Genre14"),
                    IgdbGenre("slug15", "Genre15"),
                    IgdbGenre("slug16", "Genre16"),
                    IgdbGenre("slug17", "Genre17"),
                    IgdbGenre("slug18", "Genre18"),
                )
            )

        }
    }
}


@Composable
fun PreferenceView(
    onColorsAvailable: (PixelColors) -> Unit,
    navigateToGenreSelection: () -> Unit,
    navigateToPreferenceSelection: () -> Unit
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
                navigate = navigateToGenreSelection
            )

            PreferenceNavigationLink(
                colors = colors,
                "Select Platforms",
                navigate = navigateToPreferenceSelection
            )


            FilledTonalButton(
                onClick = {},
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
    navigate: () -> Unit
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
            imageVector = if (true) Icons.Default.ChevronRight else Icons.Default.Check,
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
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors.contrastColor)
            ) {
                itemsIndexed(preferences) { index, item ->
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
                    if (index != preferences.lastIndex) {
                        HorizontalDivider(color = colors.backgroundColor)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreferenceViewPreview() {
    PreferenceNavHost()
}