package com.example.loadingimages.domain.usecases.photos

import android.util.Log
import com.example.loadingimages.core.ApiException
import com.example.loadingimages.core.catchInternal
import com.example.loadingimages.domain.models.PhotoItem
import com.example.loadingimages.domain.repositories.PhotoRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhotosUseCasesImpl(private val photoRepository: PhotoRepository) : PhotosUseCases {

    override fun invoke(page: Int, perPage: Int): Flow<Result<List<PhotoItem>, ApiException>> =
        flow {
            when (val result = photoRepository.getPhotos(page, perPage)) {
                is Ok -> {
                    Log.d("TAG", "invoke: ${result.value}")
                    if (result.value.isNotEmpty()) emit(Ok(result.value))
                    else emit(Err(ApiException.NotIdentified))
                }

                is Err -> emit(Err(ApiException.Internal(cause = result.error,message = result.error.message)))
            }
        }.catchInternal {
            emit(it)
        }
}