package com.adewan.scout.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.adewan.scout.ui.theme.defaultHorizontalPadding
import com.adewan.scout.ui.theme.poppinsFont
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(viewModel: HomeTabViewModel = koinViewModel()) {
    LaunchedEffect(viewModel) { viewModel.getShowcaseGames() }

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
            GameListButton()
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

@Composable
private fun GameListButton() {
    var listSelectionOpen by remember { mutableStateOf(false) }

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
            "Upcoming", style = MaterialTheme.typography.headlineMedium,
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