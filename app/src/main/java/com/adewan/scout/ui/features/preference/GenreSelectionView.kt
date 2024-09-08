package com.adewan.scout.ui.features.preference

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adewan.scout.core.models.Genre
import com.adewan.scout.ui.theme.poppinsFont
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreSelectionView(
    viewModel: PreferenceSelectionViewModel = koinViewModel(),
    goBack: () -> Unit
) {
    val selectedGenres = remember { mutableStateListOf<Genre>() }

    var filter by remember { mutableStateOf("") }

    LaunchedEffect(viewModel) {
        viewModel.getGenres()
    }

    LaunchedEffect(viewModel) {
        selectedGenres.clear()
        selectedGenres.addAll(viewModel.getSelectedGenres())
    }

    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(title = {
            TextField(
                value = filter, onValueChange = { filter = it },
                placeholder = {
                    Text(
                        "Type to filter list", color = Color(0xFF9CB5B7),
                        fontFamily = poppinsFont,
                        fontWeight = FontWeight.Thin
                    )
                },
                colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF9CB5B7),
                    unfocusedIndicatorColor = Color(0xFF9CB5B7).copy(alpha = 0.5f),
                    cursorColor = Color(0xFF9CB5B7)
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF9CB5B7)
                ),
            )
        }, navigationIcon = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "",
                modifier = Modifier.clickable { goBack() },
                tint = Color(0xFF9CB5B7)
            )
        }, modifier = Modifier.padding(horizontal = 16.dp), actions = {
            Text(
                "Save",
                color = Color(0xFF9CB5B7),
                fontFamily = poppinsFont,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(enabled = selectedGenres.isNotEmpty()) {
                        scope.launch {
                            viewModel.saveSelectedGenres(selectedGenres)
                        }
                        goBack()
                    })

        })
    }) {
        val filteredGenreList = if (filter.isNotEmpty()) {
            viewModel.genres.filter { it.name.startsWith(filter, ignoreCase = true) }
        } else {
            viewModel.genres
        }
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp),
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            items(filteredGenreList) { genre ->
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedGenres.contains(genre),
                            onCheckedChange = { newValue ->
                                if (newValue) {
                                    selectedGenres.add(genre)
                                } else {
                                    selectedGenres.remove(genre)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF9CB5B7),
                                uncheckedColor = Color(0xFF9CB5B7),
                                checkmarkColor = Color.White
                            )
                        )
                        Text(genre.name, color = Color(0xFF9CB5B7), fontFamily = poppinsFont)
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}