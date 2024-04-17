package com.example.loadingimages.core

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun saveImage(bitmap: Bitmap, context: Context, imageId: String?) {
    val storageDir: File = context.cacheDir

    val filePath = File(storageDir, "$imageId.jpg").path
    try {
        FileOutputStream(filePath).use { out ->
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                out
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getImages(context: Context): ArrayList<File> {
    val listFiles: ArrayList<File> = arrayListOf()
    val storageDir: File = context.cacheDir
    storageDir.listFiles()?.forEach {
        if (it.name.contains(".jpg")) {
            listFiles.add(it)
        }

    }
    return listFiles
}
