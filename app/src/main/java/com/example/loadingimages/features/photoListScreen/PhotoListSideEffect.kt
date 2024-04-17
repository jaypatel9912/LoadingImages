package com.example.loadingimages.features.photoListScreen

sealed interface PhotoListSideEffect {
    data class ShowApiError(val message: String = "") : PhotoListSideEffect
}