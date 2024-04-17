package com.example.loadingimages.core
sealed class ApiException(cause: Throwable?, message: String?) : Throwable(message, cause) {
    data class ServerError(val code: ApiStatusCode? = null, override val message: String? = null) :
        ApiException(null, message)

    data class Internal(
        override val cause: Throwable? = null,
        override val message: String? = null
    ) : ApiException(cause, message)

    data object NotIdentified : ApiException(null, null) {
        private fun readResolve(): Any = NotIdentified
    }
    data object Forbidden :ApiException(cause = null,message = null) {
        private fun readResolve(): Any = Forbidden
    }


}