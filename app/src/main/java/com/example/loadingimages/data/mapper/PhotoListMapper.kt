package com.example.loadingimages.data.mapper

import com.example.loadingimages.domain.models.PhotoItem
import com.example.loadingimages.domain.models.PhotosResponseItem

fun PhotosResponseItem.toModelPhotos(): PhotoItem {
    return PhotoItem(name = alt_description, imageUrl = urls?.thumb,imageId = id)
}