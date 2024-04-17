package com.example.loadingimages.core

import kotlinx.serialization.Serializable

@Serializable
data class ApiError<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null,
)
