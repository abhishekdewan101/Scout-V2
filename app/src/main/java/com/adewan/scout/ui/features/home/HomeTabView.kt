package com.adewan.scout.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Tune
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adewan.scout.ui.theme.defaultHorizontalPadding
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.buildReleaseDateString
import com.adewan.scout.utils.twoDecimalPlaces
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(viewModel: HomeTabViewModel = koinViewModel(), onImageShown: (String) -> Unit) {
    LaunchedEffect(viewModel) { viewModel.getShowcaseGames() }

    BoxWithConstraints {
        val modalBottomSheetHeight = constraints.maxHeight.dp / 2f
        val maxWidth = maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = spacedBy(20.dp)
        ) {
            Header(viewModel = viewModel, modalBottomSheetHeight = modalBottomSheetHeight)
            if (viewModel.viewState is HomeViewState.Loading) {
                LoadingSkeleton(width = maxWidth / 1.75f)
            }
            if (viewModel.viewState is HomeViewState.Success) {
                GameShowcasePager(
                    viewModel = viewModel,
                    width = maxWidth / 1.75f,
                    onImageShown = onImageShown
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultHorizontalPadding)
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Most Anticipated",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
                Icon(Icons.Outlined.Tune,
                    "",
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { }
                        .border(
                            1.dp,
                            Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingSkeleton(width: Dp) {
    Row(
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(brush = imagePlaceHolder.brush)
                    .width(width)
                    .aspectRatio(3 / 4f)
            )
        }
    }
}


val imagePlaceHolder by lazy {
    BrushPainter(
        Brush.linearGradient(
            listOf(
                Color(color = 0xFF2196F3),
                Color(color = 0xFFE91E63),
            )
        )
    )
}


@Composable
private fun GameShowcasePager(
    viewModel: HomeTabViewModel,
    width: Dp,
    onImageShown: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { viewModel.currentListTypeGames.size })

    LaunchedEffect(viewModel.currentListType) {
        pagerState.scrollToPage(0)
    }


    onImageShown(viewModel.currentListTypeGames[pagerState.currentPage].poster?.largeImage.toString())

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth(),
        pageSize = PageSize.Fixed(width + 10.dp)
    ) { page ->
        val game = viewModel.currentListTypeGames[page]
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.poster?.smallImage)
                    .crossfade(true)
                    .build(),
                placeholder = imagePlaceHolder,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(width)
                    .aspectRatio(3 / 4f)
                    .clickable { }
            )
            when {
                game.rating != null -> {
                    Box(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            game.rating.twoDecimalPlaces(),
                            fontFamily = poppinsFont,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                game.firstReleaseDate != null -> {
                    Box(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            game.firstReleaseDate.buildReleaseDateString(),
                            fontFamily = poppinsFont,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(
    viewModel: HomeTabViewModel,
    modalBottomSheetHeight: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = defaultHorizontalPadding)
            .padding(top = 20.dp),
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
            onDismissRequest = { listSelectionOpen = listSelectionOpen.not() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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