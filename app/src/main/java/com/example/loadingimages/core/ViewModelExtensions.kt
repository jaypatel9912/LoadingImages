package com.example.loadingimages.core

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

@SuppressLint("ComposableNaming")
@Composable
fun <STATE : Any, INTENT : Any, SIDE_EFFECT : Any> BaseViewModel<STATE, INTENT, SIDE_EFFECT>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)
) {
    val sideEffectFlow = effect
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect { sideEffect(it) }
        }
    }
}
