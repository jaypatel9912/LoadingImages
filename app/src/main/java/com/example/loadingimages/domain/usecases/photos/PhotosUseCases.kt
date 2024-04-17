package com.example.loadingimages.domain.usecases.photos

import com.example.loadingimages.core.ApiException
import com.example.loadingimages.domain.models.PhotoItem
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface PhotosUseCases {
    operator fun invoke(page: Int, perPage: Int): Flow<Result<List<PhotoItem>, ApiException>>
}