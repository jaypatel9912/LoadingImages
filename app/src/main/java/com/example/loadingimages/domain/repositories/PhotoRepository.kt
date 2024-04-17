package com.example.loadingimages.domain.repositories

import com.example.loadingimages.core.ApiException
import com.example.loadingimages.domain.models.PhotoItem
import com.github.michaelbull.result.Result

interface PhotoRepository {
    suspend fun getPhotos(page: Int, perPage: Int): Result<List<PhotoItem>, ApiException>
}