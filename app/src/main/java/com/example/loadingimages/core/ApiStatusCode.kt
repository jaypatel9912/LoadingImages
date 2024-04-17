package com.example.loadingimages.core

enum class ApiStatusCode(val code: Int) {
    Success(200), UnAuthorized(401), ServerError(500);

    companion object {
        fun getApiCode(code: Int?) = when (code) {
            200 -> Success
            401 -> UnAuthorized
            else -> ServerError
        }
    }
}

