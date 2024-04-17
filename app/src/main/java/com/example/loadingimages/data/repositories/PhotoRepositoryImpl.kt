package com.example.loadingimages.data.repositories

import com.example.loadingimages.core.ApiException
import com.example.loadingimages.data.mapper.toModelPhotos
import com.example.loadingimages.data.remote.ApiService
import com.example.loadingimages.domain.repositories.PhotoRepository
import com.github.michaelbull.result.mapEither

class PhotoRepositoryImpl(private val apiService: ApiService) : PhotoRepository {
    override suspend fun getPhotos(page: Int, perPage: Int) =
        apiService.getPhotos(
            page, perPage
        ).mapEither(
            success = {
                it.map { it.toModelPhotos() }
                      },
            failure = { if (it.code == 403) ApiException.Forbidden else ApiException.ServerError() }
        )

}