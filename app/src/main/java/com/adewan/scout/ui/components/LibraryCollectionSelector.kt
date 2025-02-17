package com.adewan.scout.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.adewan.scout.R
import com.adewan.scout.ui.theme.ScoutColors

@Composable
fun LibraryCollectionSelector(
    modifier: Modifier = Modifier,
    colors: ScoutColors,
    selectedLibraryCollection: Int = -1,
    changeSelectedLibraryCollection: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .border(
                width = 2.dp, color = colors.contrastColor, shape = RoundedCornerShape(10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clickable { changeSelectedLibraryCollection(0) }) {
            Icon(
                if (selectedLibraryCollection != 0) Icons.Default.CheckCircleOutline else Icons.Default.CheckCircle,
                "",
                modifier = Modifier.align(Alignment.Center),
                tint = colors.contrastColor
            )
        }

        VerticalDivider(
            thickness = 2.dp, color = colors.contrastColor
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clickable { changeSelectedLibraryCollection(1) }) {
            Icon(
                if (selectedLibraryCollection != 1) ImageVector.vectorResource(
                    id = R.drawable.favorite_outlined
                ) else Icons.Default.Favorite,
                "",
                modifier = Modifier.align(Alignment.Center),
                tint = colors.contrastColor
            )
        }
        VerticalDivider(
            thickness = 2.dp, color = colors.contrastColor
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clickable { changeSelectedLibraryCollection(2) }) {
            Icon(
                if (selectedLibraryCollection != 2) ImageVector.vectorResource(R.drawable.bookmark_outline) else Icons.Default.Bookmark,
                "",
                modifier = Modifier.align(Alignment.Center),
                tint = colors.contrastColor
            )
        }
    }
}