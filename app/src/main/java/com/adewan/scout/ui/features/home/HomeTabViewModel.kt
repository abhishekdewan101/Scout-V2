package com.adewan.scout.ui.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class ShowcaseListType(val title: String) {
    Upcoming("Upcoming"),
    Recent("Recently Released")
}

class HomeTabViewModel : ViewModel() {
    var currentSelectedList by mutableStateOf(ShowcaseListType.Upcoming)
        private set

    fun updateCurrentSelectedList(listType: ShowcaseListType) {
        currentSelectedList = listType
    }
}