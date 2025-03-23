package com.adewan.scout.ui.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adewan.scout.ui.components.GamePoster
import com.adewan.scout.ui.components.GameRow
import com.adewan.scout.ui.components.LibraryCollectionSelector
import com.adewan.scout.ui.components.imagePlaceHolder
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.downloadbleContent
import com.adewan.scout.utils.dummyStoryline
import com.adewan.scout.utils.games
import com.adewan.scout.utils.screenshots

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsView(
    colors: PixelColors,
    slug: String,
    navigateToDetailView: (String) -> Unit,
    navigateToSearchView: () -> Unit,
    navigateBack: () -> Unit
) {
    var userSelectedLibraryCollection by remember { mutableIntStateOf(-1) }

    Scaffold(containerColor = colors.backgroundColor) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = spacedBy(16.dp),
        ) {
            item {
                DetailViewHeader(
                    colors = colors,
                    navigateToSearchView = navigateToSearchView,
                    navigateBack = navigateBack
                )
            }
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://static.cdprojektred.com/cms.cdprojektred.com/16x9_big/f4ce7b1be9bc4b0ee2df8f9a311a850a594d3d1a-1920x1080.jpg")
                        .crossfade(true).build(),
                    contentDescription = "",
                    placeholder = imagePlaceHolder,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(MaterialTheme.shapes.medium)

                )
            }
            item {
                LibraryCollectionSelector(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = colors,
                    selectedLibraryCollection = userSelectedLibraryCollection,
                    changeSelectedLibraryCollection = {
                        userSelectedLibraryCollection = it
                    })
            }

            item {
                GameName(colors)
            }

            item {
                GameTrailer(colors)
            }

            item {
                Text(
                    dummyStoryline,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = TextStyle(
                        color = colors.contrastColor, fontSize = 12.sp, fontFamily = poppinsFont
                    )
                )
            }

            item {
                ScreenshotsPager(colors = colors)
            }

            item {
                GameGenres(colors = colors)
            }

            item {
                GamePlatforms(colors = colors)
            }

            item {
                HorizontalDivider(color = colors.contrastColor.copy(alpha = 0.5f))
            }

            item {
                RatingRow(colors = colors)
            }

            item {
                HorizontalDivider(color = colors.contrastColor.copy(alpha = 0.5f))
            }

            item {
                DownloadableContentPager(
                    colors = colors, navigateToDetailView = navigateToDetailView
                )
            }

            item {
                HorizontalDivider(color = colors.contrastColor.copy(alpha = 0.5f))
            }

            item {
                SimilarGamesPager(colors = colors, navigateToDetailView = navigateToDetailView)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SimilarGamesPager(colors: PixelColors, navigateToDetailView: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Similar Games", style = TextStyle(
                fontSize = 24.sp,
                color = colors.contrastColor,
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Bold
            )
        )

        val pagerState = rememberPagerState(pageCount = {
            games.size
        })

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            val game = games[it]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { navigateToDetailView(game.slug) }) {
                GamePoster(
                    modifier = Modifier.align(Alignment.Center),
                    posterUrl = game.posterUrl,
                    width = 200.dp,
                    height = 300.dp,
                    accessoryText = "Dec 27th, 2025"
                )
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) colors.contrastColor else colors.contrastColor.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)

                )
            }
        }
    }
}

@Composable
private fun DownloadableContentPager(colors: PixelColors, navigateToDetailView: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            "Downloadable Content", style = TextStyle(
                fontSize = 24.sp,
                color = colors.contrastColor,
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Bold
            )
        )

        val pagerState = rememberPagerState(pageCount = {
            downloadbleContent.size
        })

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            val game = downloadbleContent[it]
            GameRow(
                modifier = Modifier.clickable { navigateToDetailView(game.slug) },
                posterUrl = game.posterUrl,
                title = game.name,
                subTitle = game.publisher,
                index = it,
                textColor = colors.contrastColor,
                rating = game.rating.toString(),
                platform = game.platform
            )
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) colors.contrastColor else colors.contrastColor.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)

                )
            }
        }
    }
}

@Composable
private fun RatingRow(colors: PixelColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "89.95%", style = TextStyle(
                fontSize = 32.sp,
                color = colors.contrastColor,
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            "293 Reviews", style = TextStyle(
                fontSize = 16.sp,
                color = colors.contrastColor.copy(alpha = 0.5f),
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun GamePlatforms(colors: PixelColors) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            "Platforms", style = TextStyle(
                color = colors.contrastColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFont
            )
        )
        FlowRow(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = spacedBy(10.dp),
            verticalArrangement = spacedBy(10.dp)
        ) {
            listOf("Playstation 5", "Xbox One", "Nintendo Switch").forEach {
                GameTag(colors = colors, tagName = it)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun GameGenres(colors: PixelColors) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            "Genre", style = TextStyle(
                color = colors.contrastColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFont
            )
        )
        FlowRow(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = spacedBy(10.dp),
            verticalArrangement = spacedBy(10.dp)
        ) {
            listOf(
                "Action/Adventure",
                "Survival Horror",
                "Racing",
                "Open World",
                "Puzzle",
                "Role Playing Game"
            ).forEach {
                GameTag(colors = colors, tagName = it)
            }
        }
    }
}

@Composable
private fun GameTag(colors: PixelColors, tagName: String) {
    Box(
        modifier = Modifier
            .background(
                color = colors.contrastColor.copy(alpha = 0.5f), shape = RoundedCornerShape(5.dp)
            )
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Text(
            tagName, style = TextStyle(
                color = colors.backgroundColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFont
            )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenshotsPager(colors: PixelColors) {

    var isExpandedViewer by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(pageCount = {
        screenshots.size
    })

    if (isExpandedViewer) {
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red), properties = DialogProperties(
                usePlatformDefaultWidth = false, decorFitsSystemWindows = false
            ), onDismissRequest = { isExpandedViewer = !isExpandedViewer }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                ) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(screenshots[page])
                            .crossfade(true).build(),
                        contentDescription = "",
                        placeholder = imagePlaceHolder,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize()

                    )
                }

                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) colors.contrastColor else colors.contrastColor.copy(
                                alpha = 0.5f
                            )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)

                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(screenshots[page])
                    .crossfade(true).build(),
                contentDescription = "",
                placeholder = imagePlaceHolder,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.medium)

            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clip(CircleShape)
                .background(colors.backgroundColor)
                .padding(16.dp)
                .clickable {
                    isExpandedViewer = !isExpandedViewer
                }) {
            Icon(Icons.Default.OpenInFull, "", tint = colors.contrastColor)
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) colors.contrastColor else colors.contrastColor.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)

                )
            }
        }
    }
}

@Composable
private fun GameTrailer(colors: PixelColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://preview.redd.it/p02h2jzr2yn51.jpg?auto=webp&s=01eef7388968905c5a3fbdf545825e1950824077")
                .crossfade(true).build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(300.dp)
        )
        Icon(
            Icons.Default.PlayArrow,
            "",
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = colors.backgroundColor.copy(alpha = 0.7f), shape = CircleShape)
                .padding(16.dp)
                .size(36.dp),
            tint = colors.contrastColor
        )
    }
}

@Composable
private fun GameName(colors: PixelColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "CyberPunk 2077", style = TextStyle(
                color = colors.contrastColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFont
            )
        )

        Text(
            "CD Project Red, 2022", style = TextStyle(
                color = colors.contrastColor.copy(alpha = 0.5f),
                fontSize = 16.sp,
                fontFamily = poppinsFont
            )
        )
    }
}

@Composable
private fun DetailViewHeader(
    colors: PixelColors,
    navigateToSearchView: () -> Unit,
    navigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colors.backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = navigateBack) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = colors.contrastColor
            )
        }

        Column(
            modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Release Date", style = TextStyle(
                    color = colors.contrastColor.copy(alpha = 0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFont
                )
            )
            Text(
                "March 28th, 2024", style = TextStyle(
                    color = colors.contrastColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFont
                )
            )
        }

        IconButton(onClick = navigateToSearchView) {
            Icon(
                Icons.Default.Search, contentDescription = "Search", tint = colors.contrastColor
            )
        }
    }
}