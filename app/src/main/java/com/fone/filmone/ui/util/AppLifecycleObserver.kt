package com.fone.filmone.ui.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver(
    private val onEvent: (event: Lifecycle.Event) -> Unit
) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        onEvent(event)
    }
}