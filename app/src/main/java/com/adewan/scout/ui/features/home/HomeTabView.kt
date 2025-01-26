package com.adewan.scout.ui.features.home

import android.graphics.drawable.BitmapDrawable
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.adewan.scout.ui.components.GamePoster
import com.adewan.scout.ui.components.GameRow
import com.adewan.scout.ui.components.buildGameRowSubTitle
import com.adewan.scout.ui.components.imagePlaceHolder
import com.adewan.scout.ui.theme.defaultHorizontalPadding
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.buildReleaseDateString
import com.adewan.scout.utils.twoDecimalPlaces
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTabView(viewModel: HomeTabViewModel = koinViewModel(), onImageShown: (Color) -> Unit) {
    LaunchedEffect(viewModel) { viewModel.getShowcaseGames() }
    val homeTabColor = LocalHomeTabColors.current

    BoxWithConstraints {
        val maxWidth = maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Header(viewModel = viewModel)
            if (viewModel.viewState is HomeViewState.Loading) {
                LoadingSkeleton(width = maxWidth / 2f)
            }
            if (viewModel.viewState is HomeViewState.Success) {
                GameShowcasePager(
                    viewModel = viewModel,
                    width = maxWidth / 2f,
                    onImageShown = onImageShown
                )
            }
            AllTimeBestListHeader(homeTabColor)
            if (viewModel.viewState is HomeViewState.Success) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 15.dp),
                    verticalArrangement = spacedBy(20.dp)
                ) {
                    (viewModel.viewState as HomeViewState.Success).allTimeBestGames.forEach { game ->
                        GameRow(
                            posterUrl = game.poster?.smallImage ?: "",
                            title = game.name,
                            subTitle = game.buildGameRowSubTitle(),
                            platform = game.platforms.take(3)
                                .joinToString(separator = ",") { it.name },
                            index = 0,
                            rating = game.rating.twoDecimalPlaces(),
                            textColor = homeTabColor.contrastColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AllTimeBestListHeader(homeTabColor: HomeTabColor) {
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
            "All Time Best",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFont,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = homeTabColor.contrastColor,
        )
        Icon(Icons.Outlined.Tune,
            "",
            tint = homeTabColor.contrastColor,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { }
                .border(
                    1.dp,
                    homeTabColor.contrastColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(vertical = 5.dp, horizontal = 10.dp)
        )
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


@Composable
private fun GameShowcasePager(
    viewModel: HomeTabViewModel,
    width: Dp,
    onImageShown: (Color) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { viewModel.currentListTypeGames.size })

    LaunchedEffect(viewModel.currentListType) {
        pagerState.scrollToPage(0)
    }

    val context = LocalContext.current
    LaunchedEffect(pagerState.currentPage) {
        val imageLoader = ImageLoader.Builder(context).build()
        val imageRequest = ImageRequest.Builder(context)
            .size(600, 600)
            .data(viewModel.currentListTypeGames[pagerState.currentPage].poster?.largeImage)
            .allowHardware(false)
            .build()
        val result = imageLoader.execute(imageRequest)
        val bitmap = if (result is SuccessResult) {
            (result.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
        if (bitmap != null) {
            Palette.from(bitmap).generate { colors ->
                if (colors != null) {
                    var color = colors.getDominantColor(0)
                    if (color == 0) {
                        color = colors.getVibrantColor(0)
                    }
                    onImageShown(Color(color))
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        pageSize = PageSize.Fixed(width + 15.dp)
    ) { page ->
        val game = viewModel.currentListTypeGames[page]
        GamePoster(
            posterUrl = game.poster?.smallImage ?: "",
            width = width,
            accessoryText = game.rating.twoDecimalPlaces()
                ?: game.firstReleaseDate?.buildReleaseDateString()
        )
    }
}

@Composable
private fun Header(viewModel: HomeTabViewModel) {
    val homeTabColor = LocalHomeTabColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = defaultHorizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GameListButton(
            currentListType = viewModel.currentListType,
            onGameListSelect = viewModel::changeListType
        )
        Icon(
            Icons.Default.Search,
            "",
            tint = homeTabColor.contrastColor,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameListButton(
    currentListType: GameListType,
    onGameListSelect: (GameListType) -> Unit
) {
    var listSelectionOpen by remember { mutableStateOf(false) }
    val homeTabColor = LocalHomeTabColors.current

    if (listSelectionOpen) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(confirmValueChange = { false }),
            containerColor = homeTabColor.backgroundColor,
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
                            color = if (it == currentListType) homeTabColor.contrastColor else homeTabColor.contrastColor.copy(
                                alpha = 0.5f
                            ),
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
            currentListType.label,
            style = TextStyle(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            ),
            color = homeTabColor.contrastColor,
        )
        Icon(
            if (listSelectionOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            "",
            modifier = Modifier.size(28.dp),
            tint = homeTabColor.contrastColor
        )
    }
}