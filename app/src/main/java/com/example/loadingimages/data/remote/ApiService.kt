package com.example.loadingimages.data.remote

import com.example.loadingimages.core.ApiError
import com.example.loadingimages.core.getResponse
import com.example.loadingimages.domain.models.PhotosResponseItem
import com.github.michaelbull.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ApiService(private val httpClient: HttpClient) {

    suspend fun getPhotos(page: Int, per_page: Int): Result<List<PhotosResponseItem>, ApiError<Unit>> {
        return httpClient.get {
            url("/photos")
            parameter("page", page)
            parameter("per_page", per_page)
            parameter("client_id", "Df9pP0TwWAucQOiwySEjgIgE8ukGyw8bDB8RfV-93lE")
        }.getResponse()
    }
}
