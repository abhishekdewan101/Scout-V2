package com.adewan.scout.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.defaultScoutColor
import com.adewan.scout.ui.theme.poppinsFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropDownMenu(
    modifier: Modifier = Modifier,
    colors: PixelColors,
    currentFilterValue: String,
    currentSelectedFilterIndices: List<Int>,
    filterOptionList: List<String>,
    onFilterOptionsSelected: (List<Int>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.border(
            width = 2.dp,
            color = colors.contrastColor.copy(alpha = 0.7f),
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                currentFilterValue, style = TextStyle(
                    color = colors.contrastColor.copy(alpha = 0.7f), fontSize = 18.sp
                )
            )
            Icon(Icons.Default.KeyboardArrowDown, "", tint = colors.contrastColor)
        }
    }

    val currentSelectedIndex =
        remember { mutableStateListOf<Int>().apply { addAll(currentSelectedFilterIndices) } }

    if (expanded) {
        ModalBottomSheet(
            onDismissRequest = {
                expanded = !expanded
                onFilterOptionsSelected(currentSelectedIndex)
            },
            containerColor = colors.backgroundColor,
            dragHandle = { BottomSheetDefaults.DragHandle(color = colors.contrastColor) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp, top = 16.dp),
                verticalArrangement = spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                filterOptionList.forEachIndexed { index, filter ->
                    FilterDropDownItem(
                        modifier = Modifier
                            .clickable {
                                currentSelectedIndex.add(index)
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        item = filter,
                        isSelected = currentSelectedIndex.contains(index),
                        colors = colors)
                }

                FilledTonalButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        expanded = !expanded
                        onFilterOptionsSelected(currentSelectedIndex)
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = colors.contrastColor, contentColor = colors.backgroundColor
                    )
                ) {
                    Text("Apply")
                }
            }
        }
    }
}

@Composable
fun FilterDropDownItem(
    modifier: Modifier = Modifier,
    colors: PixelColors,
    item: String,
    isSelected: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (isSelected) {
            Icon(Icons.Filled.RadioButtonChecked, "", tint = colors.contrastColor)
        } else {
            Icon(Icons.Outlined.RadioButtonUnchecked, "", tint = colors.contrastColor)
        }

        Text(
            item, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold,
                color = colors.contrastColor
            )
        )
    }
}


@Preview
@Composable
fun PreviewFilterDropDownMenu() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp, top = 16.dp),
        verticalArrangement = spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterDropDownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = defaultScoutColor,
            currentFilterValue = "All Platforms",
            filterOptionList = listOf(
                "Option 1", "Option 2", "Option 3", "Option 4", "Option 5"
            ),
            currentSelectedFilterIndices = listOf(0, 4),
            onFilterOptionsSelected = {})
    }
}