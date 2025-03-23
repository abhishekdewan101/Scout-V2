@file:OptIn(ExperimentalMaterial3Api::class)

package com.adewan.scout.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.components.FilterDropDownMenu
import com.adewan.scout.ui.components.GamePoster
import com.adewan.scout.ui.components.GameRow
import com.adewan.scout.ui.components.Header
import com.adewan.scout.ui.components.SpacerPoster
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.ExtractColorFromImage
import com.adewan.scout.utils.games
import com.adewan.scout.utils.imageList
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeTabView(
    viewModel: HomeTabViewModel = koinViewModel(),
    colors: PixelColors,
    onColorsChanged: (PixelColors) -> Unit,
    onSearchIconPressed: () -> Unit,
    onItemPressed: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    var showListSelectionBottomSheet by remember { mutableStateOf(false) }

    var showFilterBottomSheet by remember { mutableStateOf(false) }

    val currentSelectedPlatforms = remember { mutableStateListOf<Int>() }
    val currentSelectedGenres = remember { mutableStateListOf<Int>() }
    val currentSelectedReleaseWindows = remember { mutableStateListOf<Int>() }

    if (showListSelectionBottomSheet) {
        ShowcaseListBottomSheet(
            selectedList = viewModel.currentSelectedList,
            onChangeSelectedList = {
                viewModel.updateCurrentSelectedList(it)
                showListSelectionBottomSheet = !showListSelectionBottomSheet
            },
            colors = colors,
            onDismiss = { showListSelectionBottomSheet = !showListSelectionBottomSheet })
    }

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

    LazyColumn(
        state = lazyListState, modifier = Modifier.fillMaxSize()
    ) {
        item {
            Header(
                modifier = Modifier.padding(top = 16.dp),
                title = viewModel.currentSelectedList.title,
                contrastColor = colors.contrastColor,
                headerIcon = if (showListSelectionBottomSheet) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                secondaryIcon = Icons.Default.Search,
                onSecondaryIconClicked = onSearchIconPressed,
                onHeaderClicked = {
                    showListSelectionBottomSheet = !showListSelectionBottomSheet
                })
        }
        item {
            ShowcaseListPager(
                items = imageList, onColorsChanged = onColorsChanged, onItemPressed = onItemPressed
            )
        }
        item {
            Header(
                modifier = Modifier.padding(vertical = 16.dp),
                title = "Highly Rated",
                contrastColor = colors.contrastColor,
                secondaryIcon = Icons.Default.FilterAlt,
                onSecondaryIconClicked = {
                    showFilterBottomSheet = !showFilterBottomSheet
                },
            )
        }

        itemsIndexed(games) { index, game ->
            GameRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onItemPressed(game.slug) },
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


@Composable
private fun ShowcaseListPager(
    items: List<String>, onColorsChanged: (PixelColors) -> Unit, onItemPressed: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { items.size + 1 })

    LaunchedEffect(items) {
        pagerState.scrollToPage(0)
    }

    if (pagerState.currentPage < items.size) {
        ExtractColorFromImage(
            currentIndex = pagerState.currentPage, items = items, onColorsChanged = onColorsChanged
        )
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        pageSize = PageSize.Fixed(250.dp + 15.dp)
    ) { page ->
        if (page == items.size) {
            SpacerPoster(width = 250.dp, height = 350.dp)
        } else {
            GamePoster(
                modifier = Modifier.clickable { onItemPressed("slug") },
                posterUrl = items[page],
                width = 250.dp,
                height = 350.dp,
                accessoryText = "Dec 27th, 2025"
            )
        }
    }
}

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
private fun ShowcaseListBottomSheet(
    selectedList: ShowcaseListType,
    onChangeSelectedList: (ShowcaseListType) -> Unit,
    colors: PixelColors,
    onDismiss: () -> Unit
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
                .height(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowcaseListType.entries.forEach {
                Text(
                    it.title, modifier = Modifier
                        .padding(vertical = 24.dp)
                        .clickable {
                            onChangeSelectedList(it)
                        }, style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = poppinsFont,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedList == it) colors.contrastColor else colors.contrastColor.copy(
                            alpha = 0.7f
                        )
                    )
                )
            }
        }
    }
}