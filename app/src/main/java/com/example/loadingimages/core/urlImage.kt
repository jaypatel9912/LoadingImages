package com.example.loadingimages.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

fun urlImage(
    url: String,
    onSave: (Bitmap) -> Unit = {},
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val imageUrl = URL(url)
            val inputStream = imageUrl.openConnection().getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            onSave(bitmap)

        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}
