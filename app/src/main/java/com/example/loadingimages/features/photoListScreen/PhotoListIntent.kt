package com.example.loadingimages.features.photoListScreen

import android.content.Context

sealed class PhotoListIntent {
    data object Init : PhotoListIntent()
    data class SetContext(val context: Context): PhotoListIntent()
}