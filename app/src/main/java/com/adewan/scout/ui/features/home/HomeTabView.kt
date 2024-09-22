package com.adewan.scout.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adewan.scout.ui.theme.defaultHorizontalPadding
import com.adewan.scout.ui.theme.poppinsFont
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(viewModel: HomeTabViewModel = koinViewModel()) {
    LaunchedEffect(viewModel) { viewModel.getShowcaseGames() }

    BoxWithConstraints {
        val modalBottomSheetHeight = constraints.maxHeight.dp / 2f
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultHorizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameListButton(
                    currentListType = viewModel.currentListType,
                    modalBottomSheetHeight = modalBottomSheetHeight,
                    onGameListSelect = viewModel::changeListType
                )
                Icon(
                    Icons.Default.Search,
                    "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { }
                        .padding(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameListButton(
    currentListType: GameListType,
    modalBottomSheetHeight: Dp,
    onGameListSelect: (GameListType) -> Unit
) {
    var listSelectionOpen by remember { mutableStateOf(false) }

    if (listSelectionOpen) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(confirmValueChange = { false }),
            modifier = Modifier.height(modalBottomSheetHeight),
            onDismissRequest = { listSelectionOpen = listSelectionOpen.not() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(modalBottomSheetHeight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameListType.entries.forEach {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onGameListSelect(it)
                            listSelectionOpen = false
                        }
                        .padding(10.dp)
                        .padding(vertical = 25.dp)) {
                        Text(
                            it.label,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            fontFamily = poppinsFont,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        )
                    }
                }

            }
        }
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                listSelectionOpen = listSelectionOpen.not()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(5.dp)
    ) {
        Text(
            currentListType.label, style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        if (listSelectionOpen) {
            Icon(Icons.Default.KeyboardArrowUp, "")
        } else {
            Icon(Icons.Default.KeyboardArrowDown, "")
        }
    }
}