@file:OptIn(ExperimentalMaterial3Api::class)

package com.adewan.scout.ui.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adewan.scout.ui.components.Header
import com.adewan.scout.ui.theme.ScoutColors

@Composable
fun HomeTabView(
    colors: ScoutColors,
    onColorsChanged: (ScoutColors) -> Unit,
    onSearchIconPressed: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    var showListSelectionBottomSheet by remember { mutableStateOf(false) }

    if (showListSelectionBottomSheet) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            containerColor = colors.backgroundColor,
            dragHandle = { BottomSheetDefaults.DragHandle(color = colors.contrastColor) },
            onDismissRequest = {
                showListSelectionBottomSheet = showListSelectionBottomSheet.not()
            }) {
            Box(Modifier.height(400.dp))
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Header(
                modifier = Modifier.padding(top = 16.dp),
                title = "Upcoming",
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