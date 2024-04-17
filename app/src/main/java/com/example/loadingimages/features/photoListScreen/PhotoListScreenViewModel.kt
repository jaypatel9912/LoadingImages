package com.example.loadingimages.features.photoListScreen

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.loadingimages.R
import com.example.loadingimages.core.ApiException
import com.example.loadingimages.core.BaseViewModel
import com.example.loadingimages.core.ListState
import com.example.loadingimages.core.getImages
import com.example.loadingimages.core.isInternetAvailable
import com.example.loadingimages.core.onLoading
import com.example.loadingimages.core.saveImage
import com.example.loadingimages.core.urlImage
import com.example.loadingimages.domain.models.PhotoItem
import com.example.loadingimages.domain.usecases.photos.PhotosUseCases
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import kotlinx.coroutines.launch


class PhotoListScreenViewModel(private val photoListUseCases: PhotosUseCases) :
    BaseViewModel<PhotoListState, PhotoListIntent, PhotoListSideEffect>(
        PhotoListState()
    ) {

    override fun onIntent(intent: PhotoListIntent) {
        launch {
            when (intent) {
                PhotoListIntent.Init -> {
                    getPhotos()
                }

                is PhotoListIntent.SetContext -> {
                    reduceState { it.copy(context = intent.context) }
                    if (isInternetAvailable(intent.context)) {
                        getPhotos()
                    } else {
                        reduceState {
                            it.copy(response = getImages(intent.context).map {
                                PhotoItem(
                                    name = it.name,
                                    imageUrl = it.path,
                                    imageId = it.name
                                )
                            })
                        }
                    }
                }
            }
        }

    }


    private fun getPhotos() {
        viewModelScope.launch {
            if (uiState.value.page == 1 || (uiState.value.page != 1 && uiState.value.canPaginate) && uiState.value.listState == ListState.IDLE) {
                reduceState { it.copy(listState = if (uiState.value.page == 1) ListState.LOADING else ListState.PAGINATING) }
                photoListUseCases(uiState.value.page, 10).onLoading { isLoading ->
                    reduceState { it.copy(isLoading = isLoading) }
                }.collect { result ->
                    when (result) {
                        is Ok -> {
                            if (result.value.isNotEmpty()) {
                                reduceState { it.copy(canPaginate = result.value.size == 10) }
                                result.value.forEach {
                                    urlImage(
                                        url = it.imageUrl ?: "",
                                    ) { bitmap ->
                                        uiState.value.context?.let { it1 ->
                                            if (it1.cacheDir.listFiles()?.map { it.name }
                                                    ?.contains("${it.imageId}.jpg") == true) {
                                                saveImage(
                                                    bitmap = bitmap,
                                                    context = it1,
                                                    "${it.imageId}1"
                                                )
                                            } else {
                                                saveImage(
                                                    bitmap = bitmap,
                                                    context = it1,
                                                    it.imageId
                                                )
                                            }
                                        }
                                    }
                                }


                                    uiState.value.context?.let { context ->
                                        val list = mutableListOf(uiState.value.response)
                                        list.addAll(listOf(result.value.map {
                                            it.copy(imageUrl =
                                            if (context.cacheDir.listFiles()?.map { it.name }
                                                    ?.contains("${it.imageId}.jpg") == true) {
                                                "${context.cacheDir}/${it.imageId}1.jpg"
                                            } else {
                                                "${context.cacheDir}/${it.imageId}.jpg"
                                            })
                                        }))
                                        reduceState {
                                            it.copy(response = list.flatten())
                                        }
                                    }


                                reduceState { it.copy(listState = ListState.IDLE) }

                                if (uiState.value.canPaginate)
                                    reduceState { it.copy(page = uiState.value.page + 1) }
                            } else {
                                postSideEffect(
                                    PhotoListSideEffect.ShowApiError(
                                        uiState.value.context?.getString(R.string.something_went_wrong)
                                            .toString()
                                    )
                                )
                                reduceState {
                                    it.copy(
                                        listState = if (uiState.value.page == 1) {
                                            ListState.ERROR
                                        } else ListState.PAGINATION_EXHAUST
                                    )
                                }
                            }
                        }

                        is Err -> {
                            if (result.error is ApiException.Internal) {
                                if (uiState.value.context?.getString(R.string.no_address_associated_with_hostname)
                                        ?.let { result.error.message?.contains(it) } == true
                                ) {
                                    Toast.makeText(
                                        uiState.value.context,
                                        uiState.value.context?.getString(R.string.please_check_your_internet_connection),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    if (uiState.value.response.isEmpty())
                                        reduceState { it.copy(listState = if (uiState.value.page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST) }

                                } else {
                                    if (result.error.cause.toString() == uiState.value.context?.getString(
                                            R.string.forbidden
                                        )
                                    ) {
                                        postSideEffect(
                                            PhotoListSideEffect.ShowApiError(
                                                result.error.cause.toString() + uiState.value.context?.getString(
                                                    R.string._403_rate_limit_exceeded
                                                )
                                            )
                                        )
                                    } else {
                                        postSideEffect(
                                            PhotoListSideEffect.ShowApiError(
                                                result.error.message
                                                    ?: uiState.value.context?.getString(R.string.unknown_error)
                                                        .toString()
                                            )
                                        )
                                    }

                                    reduceState { it.copy(listState = ListState.PAGINATION_EXHAUST) }
                                }

                            }
                            reduceState { it.copy(listState = if (uiState.value.page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST) }
                        }
                    }
                }
            }
        }


    }

    fun setPaginateValues() {
        launch {
            if (uiState.value.response.isNotEmpty()) {
                reduceState {
                    it.copy(
                        page = uiState.value.response.size / 10,
                        listState = ListState.IDLE,
                        canPaginate = true
                    )
                }
            } else {
                getPhotos()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        launch {
            reduceState {
                it.copy(
                    page = 1,
                    listState = ListState.IDLE,
                    canPaginate = false,
                )
            }
        }

    }


}