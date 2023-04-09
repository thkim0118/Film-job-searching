package com.fone.filmone.ui.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel : ViewModel() {

    private val _toastEvent = MutableStateFlow(ToastEvent())
    val toastEvent: StateFlow<ToastEvent> = _toastEvent.asStateFlow()


    fun showToast(message: String) {
        _toastEvent.update {
            it.copy(
                message = message
            )
        }
    }

    fun clearToast() {
        _toastEvent.update {
            ToastEvent(bottomPadding = it.bottomPadding)
        }
    }
}

data class ToastEvent(
    val message: String = "",
    val bottomPadding: Int = 0
)