package com.adewan.scout.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.adewan.scout.core.network.models.IgdbGame
import com.adewan.scout.ui.theme.ScoutTheme
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.buildReleaseYearString

const val IMAGE_WIDTH = 100
const val RATIO = 2 / 3f
const val IMAGE_HEIGHT = 150

fun IgdbGame.buildGameRowSubTitle(): String {
    return buildString {
        if (involvedCompanies.firstOrNull() != null) {
            append(involvedCompanies.first().company.name)
        }
        if (firstReleaseDate != null) {
            append(", ")
            append(firstReleaseDate.buildReleaseYearString())
        }
    }
}

@Composable
fun GameRow(
    modifier: Modifier = Modifier,
    posterUrl: String,
    title: String,
    subTitle: String,
    index: Int,
    textColor: Color,
    rating: String? = null,
    platform: String? = null,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = posterUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            placeholder = imagePlaceHolder,
            modifier = Modifier
                .size(width = IMAGE_WIDTH.dp, height = IMAGE_HEIGHT.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .heightIn(max = IMAGE_HEIGHT.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.height(IMAGE_HEIGHT.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        title,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFont,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        maxLines = 2,
                        color = textColor
                    )
                    Text(
                        subTitle,
                        fontFamily = poppinsFont,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        color = textColor.copy(alpha = 0.5f)
                    )
                }
                if (rating != null || platform != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        platform?.let {
                            Text(
                                it,
                                fontFamily = poppinsFont,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(1f),
                                overflow = TextOverflow.Ellipsis,
                                color = textColor
                            )
                        }
                        Spacer(modifier = Modifier.fillMaxWidth(.20f))
                        rating?.let {
                            Text(
                                "$rating",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFont,
                                fontSize = 16.sp,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGameRow() {
    ScoutTheme {
        Column(verticalArrangement = spacedBy(10.dp)) {
            GameRow(
                posterUrl = "",
                title = "Game Title",
                subTitle = "Platform1",
                index = 0,
                rating = "97.2%",
                platform = "PC (Microsoft Windows)",
                textColor = Color.White
            )

            GameRow(
                posterUrl = "",
                title = "Game Title That is Really Really Long and Can Overflow to the next line",
                subTitle = "Really Long Company Name, Year",
                index = 0,
                platform = "PC",
                textColor = Color.White
            )

            GameRow(
                posterUrl = "",
                title = "Game Title",
                subTitle = "Platform1",
                index = 0,
                textColor = Color.White
            )
        }
    }
}