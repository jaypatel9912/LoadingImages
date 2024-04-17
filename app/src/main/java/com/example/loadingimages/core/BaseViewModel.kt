package com.example.loadingimages.core

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<STATE : Any, INTENT : Any, EFFECT : Any>(initialState: STATE) :
    ViewModel(), CoroutineScope {

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + errorHandler

    // Get Current State
    val currentState: STATE
        get() = uiState.value

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<INTENT> = MutableSharedFlow()

    private val _effect: Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val intentJobMap = mutableMapOf<String, Job>()

    init {
        launch { _event.collect(::onIntent) }
    }

    suspend fun postSideEffect(sideEffect: EFFECT) =
        coroutineScope { launchUI { _effect.send(sideEffect) } }

    suspend fun reduceState(block: (STATE) -> STATE) = withUIContext {
        _uiState.value = block(uiState.value).also { onChangeState(uiState.value, it) }
    }

    protected fun onChangeState(prevState: STATE, state: STATE) {
        // EMPTY
    }

    fun sendIntent(event: INTENT) = launch { _event.emit(event) }

    fun launchWithCancel(
        strategy: LaunchStrategy,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        when (strategy) {
            is LaunchStrategy.CancelExist -> {
                intentJobMap.remove(strategy.key)?.cancel()
                launchUnique(strategy.key, context, start, block)
            }

            is LaunchStrategy.KeepExist -> {
                launchUnique(strategy.key, context, start, block)
            }
        }
    }

    private fun launchUnique(
        key: String,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        if (!intentJobMap.containsKey(key)) {
            intentJobMap[key] = launch(context, start, block).apply {
                invokeOnCompletion { intentJobMap.remove(key) }
            }
        }
    }

    protected fun onCancelLaunch(key: String) {
        intentJobMap.remove(key)?.cancel()
    }

    protected abstract fun onIntent(intent: INTENT)

    private fun handleError(throwable: Throwable) {
        Log.e("BaseViewModel", "handleError: ", throwable)
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}

sealed class LaunchStrategy {

    class CancelExist(val key: String) : LaunchStrategy()

    class KeepExist(val key: String) : LaunchStrategy()
}
