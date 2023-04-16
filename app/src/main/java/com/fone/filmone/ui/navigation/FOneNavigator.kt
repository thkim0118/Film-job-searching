package com.fone.filmone.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FOneNavigator {
    private val _destinationFlow = MutableSharedFlow<Pair<FOneDestinations, Boolean>>(extraBufferCapacity = 1)
    val destinationFlow = _destinationFlow.asSharedFlow()

    private val _routeFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val routeFlow = _routeFlow.asSharedFlow()

    fun navigateTo(destinations: FOneDestinations, isPopAll: Boolean = false) {
        _destinationFlow.tryEmit(destinations to isPopAll)
    }

    fun navigateTo(route: String) {
        _routeFlow.tryEmit(route)
    }
}

