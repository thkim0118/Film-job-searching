package com.fone.filmone.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object FOneNavigator {
    private val _routeFlow = MutableSharedFlow<NavDestinationState>(extraBufferCapacity = 1)
    val routeFlow = _routeFlow.asSharedFlow()

    fun navigateTo(navDestinationState: NavDestinationState) {
        _routeFlow.tryEmit(navDestinationState)
    }
}

data class NavDestinationState(
    val route: String,
    val isPopAll: Boolean = false,
)
