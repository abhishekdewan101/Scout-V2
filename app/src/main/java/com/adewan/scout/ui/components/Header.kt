package com.adewan.scout.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.theme.poppinsFont

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    contrastColor: Color,
    headerIcon: ImageVector? = null,
    secondaryIcon: ImageVector,
    onHeaderClicked: (() -> Unit)? = null,
    onSecondaryIconClicked: (() -> Unit)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.then(
                if (onHeaderClicked != null) Modifier.clickable {
                    onHeaderClicked()
                } else Modifier
            ),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                title,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    color = contrastColor
                )
            )
            if (onHeaderClicked != null && headerIcon != null) {
                Icon(
                    headerIcon,
                    "",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(start = 6.dp),
                    tint = contrastColor
                )
            }
        }

        Icon(
            secondaryIcon,
            "",
            tint = contrastColor,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onSecondaryIconClicked() })
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Header(
            title = "Profile",
            onSecondaryIconClicked = {},
            headerIcon = Icons.Default.KeyboardArrowDown,
            secondaryIcon = Icons.Default.Search,
            contrastColor = Color.White,
            onHeaderClicked = {})
    }
}