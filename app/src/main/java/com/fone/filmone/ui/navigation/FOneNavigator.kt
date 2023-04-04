package com.fone.filmone.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FOneNavigator {
    private val _sharedFlow =
        MutableSharedFlow<FOneDestinations>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(destinations: FOneDestinations) {
        _sharedFlow.tryEmit(destinations)
    }
}

