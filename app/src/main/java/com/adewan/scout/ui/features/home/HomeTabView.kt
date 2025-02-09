@file:OptIn(ExperimentalMaterial3Api::class)

package com.adewan.scout.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.components.Header
import com.adewan.scout.ui.theme.ScoutColors
import com.adewan.scout.ui.theme.poppinsFont
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(
    viewModel: HomeTabViewModel = koinViewModel(),
    colors: ScoutColors,
    onColorsChanged: (ScoutColors) -> Unit,
    onSearchIconPressed: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    var showListSelectionBottomSheet by remember { mutableStateOf(false) }

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

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Header(
                modifier = Modifier.padding(top = 16.dp),
                title = viewModel.currentSelectedList.title,
                contrastColor = colors.contrastColor,
                headerIcon = if (showListSelectionBottomSheet) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                onSearchClicked = onSearchIconPressed,
                onHeaderClicked = {
                    showListSelectionBottomSheet = !showListSelectionBottomSheet
                }
            )
        }
    }
}

@Composable
private fun ShowcaseListBottomSheet(
    selectedList: ShowcaseListType,
    onChangeSelectedList: (ShowcaseListType) -> Unit,
    colors: ScoutColors,
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
                    it.title,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .clickable {
                            onChangeSelectedList(it)
                        },
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = poppinsFont,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedList == it)
                            colors.contrastColor else
                            colors.contrastColor.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}