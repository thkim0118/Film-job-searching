package com.fone.filmone.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FOneNavigator {
    private val _destinationFlow = MutableSharedFlow<FOneDestinations>(extraBufferCapacity = 1)
    val destinationFlow = _destinationFlow.asSharedFlow()

    private val _routeFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val routeFlow = _routeFlow.asSharedFlow()

    private val _main = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val main = _routeFlow.asSharedFlow()

    fun navigateTo(destinations: FOneDestinations) {
        _destinationFlow.tryEmit(destinations)
    }

    fun navigateTo(route: String) {
        _routeFlow.tryEmit(route)
    }

    fun navigateToMain() {
        _main.tryEmit(Unit)
    }
}

