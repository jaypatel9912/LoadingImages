package com.example.loadingimages.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend inline fun <reified T, reified E> HttpResponse.getResponse(): Result<T, ApiError<E>> {
    return if (status.isSuccess()) Ok(body())
    else Err(ApiError(status.value, body<E>()))
}

 fun isInternetAvailable(context: Context): Boolean {
    val result: Boolean
     val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     val networkCapabilities = connectivityManager.activeNetwork ?: return false
     val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
     result = when {
         actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
         actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
         actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
         else -> false
     }
     return result
}
