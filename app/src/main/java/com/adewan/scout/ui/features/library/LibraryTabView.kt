package com.adewan.scout.ui.features.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adewan.scout.ui.components.FilterDropDownMenu
import com.adewan.scout.ui.components.GameRow
import com.adewan.scout.ui.components.Header
import com.adewan.scout.ui.components.LibraryCollectionSelector
import com.adewan.scout.ui.features.home.FilterBottomSheet
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.utils.games

@Composable
fun LibraryTabView(colors: PixelColors, onSearchIconPressed: () -> Unit) {

    var selectedLibraryCollection by remember { mutableIntStateOf(0) }
    var showFilterBottomSheet by remember { mutableStateOf(false) }

    val currentSelectedPlatforms = remember { mutableStateListOf<Int>() }
    val currentSelectedGenres = remember { mutableStateListOf<Int>() }
    val currentSelectedReleaseWindows = remember { mutableStateListOf<Int>() }

    if (showFilterBottomSheet) {
        FilterBottomSheet(
            colors = colors,
            onDismiss = { showFilterBottomSheet = !showFilterBottomSheet },
            currentSelectedPlatforms = currentSelectedPlatforms,
            onPlatformsChanged = {
                currentSelectedPlatforms.clear()
                currentSelectedPlatforms.addAll(it)
            },
            currentSelectedGenres = currentSelectedGenres,
            onGenresChanged = {
                currentSelectedGenres.clear()
                currentSelectedGenres.addAll(it)
            },
            currentSelectedReleaseWindows = currentSelectedReleaseWindows,
            onReleaseWindowsChanged = {
                currentSelectedReleaseWindows.clear()
                currentSelectedReleaseWindows.addAll(it)
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor),
        verticalArrangement = spacedBy(16.dp)
    ) {
        Header(
            modifier = Modifier.padding(top = 16.dp),
            title = "Library",
            contrastColor = colors.contrastColor,
            secondaryIcon = Icons.Default.Search,
            onSecondaryIconClicked = onSearchIconPressed
        )

        LibraryCollectionFilter(
            colors = colors,
            selectedLibraryCollection = selectedLibraryCollection,
            changeSelectedLibraryCollection = { selectedLibraryCollection = it })

        Header(
            modifier = Modifier.padding(vertical = 16.dp),
            title = "Completed",
            contrastColor = colors.contrastColor,
            secondaryIcon = Icons.Default.FilterAlt,
            onSecondaryIconClicked = {
                showFilterBottomSheet = !showFilterBottomSheet
            },
        )

        LazyColumn {
            itemsIndexed(games) { index, game ->
                GameRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                        .clickable { },
                    posterUrl = game.posterUrl,
                    title = game.name,
                    subTitle = game.publisher,
                    index = index,
                    textColor = colors.contrastColor,
                    rating = game.rating.toString(),
                    platform = game.platform
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    colors: PixelColors,
    onDismiss: () -> Unit,
    currentSelectedPlatforms: SnapshotStateList<Int>,
    onPlatformsChanged: (List<Int>) -> Unit,
    currentSelectedGenres: SnapshotStateList<Int>,
    onGenresChanged: (List<Int>) -> Unit,
    currentSelectedReleaseWindows: SnapshotStateList<Int>,
    onReleaseWindowsChanged: (List<Int>) -> Unit
) {
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        containerColor = colors.backgroundColor,
        dragHandle = { BottomSheetDefaults.DragHandle(color = colors.contrastColor) },
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 64.dp, top = 16.dp),
            verticalArrangement = spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilterDropDownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = colors,
                currentFilterValue = "All Platforms",
                currentSelectedFilterIndices = currentSelectedPlatforms,
                filterOptionList = listOf(
                    "Option 1",
                    "Option 2",
                    "Option 3",
                    "Option 4",
                    "Option 5",
                    "Option 6",
                    "Option 7",
                    "Option 8",
                    "Option 9",
                    "Option 10",
                    "Option 11",
                    "Option 12",
                    "Option 13",
                    "Option 14",
                ),
                onFilterOptionsSelected = onPlatformsChanged
            )

            FilterDropDownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = colors,
                currentFilterValue = "All Genres",
                currentSelectedFilterIndices = currentSelectedGenres,
                filterOptionList = listOf(
                    "Option 1", "Option 2", "Option 3", "Option 4", "Option 5"
                ),
                onFilterOptionsSelected = onGenresChanged
            )

            FilterDropDownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = colors,
                currentFilterValue = "Released Any Time",
                currentSelectedFilterIndices = currentSelectedReleaseWindows,
                filterOptionList = listOf(
                    "Option 1", "Option 2", "Option 3", "Option 4", "Option 5"
                ),
                onFilterOptionsSelected = onReleaseWindowsChanged
            )

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {},
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = colors.contrastColor, contentColor = colors.backgroundColor
                )
            ) {
                Text("Apply Filters")
            }
        }
    }
}

@Composable
private fun LibraryCollectionFilter(
    colors: PixelColors,
    selectedLibraryCollection: Int,
    changeSelectedLibraryCollection: (Int) -> Unit,
) {
    LibraryCollectionSelector(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = colors,
        selectedLibraryCollection = selectedLibraryCollection,
        changeSelectedLibraryCollection = changeSelectedLibraryCollection
    )
}