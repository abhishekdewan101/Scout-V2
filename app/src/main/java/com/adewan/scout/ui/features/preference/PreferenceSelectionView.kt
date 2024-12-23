package com.adewan.scout.ui.features.preference

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adewan.scout.R
import com.adewan.scout.ui.theme.poppinsFont
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceSelectionView(
    viewModel: PreferenceSelectionViewModel = koinViewModel(),
    showPlatformSelection: () -> Unit,
    showGenreSelection: () -> Unit,
    showMainNavigation: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    var platformSelected by remember { mutableStateOf(false) }
    var genresSelected by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        platformSelected = viewModel.getSelectedPlatforms().isNotEmpty()
        genresSelected = viewModel.getSelectedGenres().isNotEmpty()
    }

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Expanded,
                skipHiddenState = false,
                confirmValueChange = { false })
        ),
        sheetContent = {
            PreferenceBottomSheetContent(
                screenHeight = screenHeight,
                platformSelected = platformSelected,
                genresSelected = genresSelected,
                showPlatformSelection = showPlatformSelection,
                showGenreSelection = showGenreSelection,
                setOnboardingDone = {
                    scope.launch {
                        viewModel.setOnboardingDone()
                    }
                },
                showMainNavigation = showMainNavigation
            )
        },
        sheetPeekHeight = screenHeight.dp / 2f,
        sheetContainerColor = Color(0xFF06232A),
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(color = Color(0xFF9CB5B7))
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.preferencebackground),
                contentDescription = "Login Background",
                modifier = Modifier
                    .scale(2.5f)
                    .graphicsLayer {
                        translationY = 120f
                        translationX = 20f
                    },
                contentScale = ContentScale.FillWidth,
            )
        }
    }

}

@Composable
private fun PreferenceBottomSheetContent(
    screenHeight: Int,
    platformSelected: Boolean,
    genresSelected: Boolean,
    showPlatformSelection: () -> Unit,
    showGenreSelection: () -> Unit,
    setOnboardingDone: () -> Unit,
    showMainNavigation: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight.dp / 2f)
            .background(Color(0xFF06232A)),
        verticalArrangement = spacedBy(40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Tell us about your gaming preferences",
                fontFamily = poppinsFont,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF9CB5B7)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color(0xFF9CB5B7), MaterialTheme.shapes.medium)
                .padding(vertical = 15.dp)
                .clickable { showPlatformSelection() },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Select Gaming Platforms", fontFamily = poppinsFont,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF9CB5B7),
            )
            Spacer(modifier = Modifier.weight(1f))
            if (platformSelected) {
                Icon(Icons.Default.Check, "", tint = Color(0xFF9CB5B7))
            } else {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "", tint = Color(0xFF9CB5B7))
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color(0xFF9CB5B7), MaterialTheme.shapes.medium)
                .padding(vertical = 15.dp)
                .clickable { showGenreSelection() },
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Select Gaming Genres", fontFamily = poppinsFont,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF9CB5B7),
            )
            Spacer(modifier = Modifier.weight(1f))
            if (genresSelected) {
                Icon(Icons.Default.Check, "", tint = Color(0xFF9CB5B7))
            } else {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "", tint = Color(0xFF9CB5B7))
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }

        Text("Continue",
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable {
                    setOnboardingDone()
                    showMainNavigation()
                }
                .clip(MaterialTheme.shapes.medium)
                .border(1.dp, Color(0xFF9CB5B7), MaterialTheme.shapes.medium)
                .padding(15.dp)
        )
    }
}

@Preview
@Composable
fun PreferenceSelectionTextPreview() {
    PreferenceBottomSheetContent(
        screenHeight = 1000,
        platformSelected = true,
        genresSelected = false,
        showPlatformSelection = {},
        {},{},{}
    )
}

@Preview
@Composable
fun PreferenceSelectionViewPreview() {
    PreferenceSelectionView(
        showPlatformSelection = {},
        showGenreSelection = {},
        showMainNavigation = {})
}