package com.adewan.scout.ui.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.ui.components.GameRow
import com.adewan.scout.ui.theme.PixelColors
import com.adewan.scout.ui.theme.poppinsFont
import com.adewan.scout.utils.games

@Composable
fun SearchView(colors: PixelColors, navigateToDetailView: (String) -> Unit) {
    var searchTerm by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var results by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundColor)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            value = searchTerm,
            onValueChange = { searchTerm = it },
            singleLine = true,
            trailingIcon = {
                if (searchTerm.text.isNotEmpty()) {
                    Icon(
                        Icons.Default.Close,
                        "",
                        tint = colors.contrastColor,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                searchTerm = TextFieldValue("")
                            })
                }
            },
            keyboardActions = KeyboardActions(onSearch = {
                println("Searching")
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                results = true

            }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            textStyle = TextStyle(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = colors.contrastColor
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.backgroundColor,
                unfocusedContainerColor = colors.backgroundColor,
                focusedIndicatorColor = colors.backgroundColor,
                unfocusedIndicatorColor = colors.backgroundColor,
                cursorColor = colors.contrastColor
            )
        )

        HorizontalDivider(
            color = colors.contrastColor.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (results) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "10 Results", style = TextStyle(
                        color = colors.contrastColor,
                        fontSize = 16.sp,
                        fontFamily = poppinsFont,
                        fontWeight = FontWeight.Medium
                    )
                )
                Icon(Icons.Default.FilterAlt, "", tint = colors.contrastColor)
            }

            LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
                itemsIndexed(games) { index, game ->
                    GameRow(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { navigateToDetailView(game.slug) },
                        posterUrl = game.posterUrl,
                        title = game.name,
                        subTitle = game.publisher,
                        index = index,
                        textColor = colors.contrastColor,
                        rating = game.rating.toString(),
                        platform = game.platform
                    )
                    if (index != games.size - 1) {
                        HorizontalDivider(
                            color = colors.contrastColor.copy(alpha = 0.5f),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
