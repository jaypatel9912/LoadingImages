package com.example.loadingimages.features.photoListScreen

import android.content.Context
import com.example.loadingimages.core.ListState
import com.example.loadingimages.domain.models.PhotoItem

data class PhotoListState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    var page: Int = 1,
    var context: Context? = null,
    var canPaginate: Boolean = false,
    var listState: ListState = ListState.IDLE,
    val response: List<PhotoItem> = arrayListOf(),
)
