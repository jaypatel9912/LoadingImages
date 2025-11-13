package com.example.loadingimages.core

import android.util.Log
import com.github.michaelbull.result.Err
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Flow<T>.catchInternal(action: suspend FlowCollector<T>.(Err<ApiException>) -> Unit): Flow<T> =
    catch { error ->
        Log.d("TAG", "catchInternal: Error data Fetch ${error.cause}")
        if (error is ConnectException || error is SocketTimeoutException || error is UnknownHostException) {
            action(Err(ApiException.Internal(error.cause, error.message)))
        } else {
            action(Err(ApiException.Internal(error.cause, error.message)))
        }
    }

inline fun <T> Flow<T>.onLoading(crossinline action: suspend FlowCollector<T>.(isLoading: Boolean) -> Unit) =
    this.onStart { action(true) }.onCompletion { action(false) }
