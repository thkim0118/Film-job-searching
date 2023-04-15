package com.fone.filmone.ui.common.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel : ViewModel() {

    private val _toastEvent = MutableStateFlow(ToastEvent())
    val toastEvent: StateFlow<ToastEvent> = _toastEvent.asStateFlow()


    fun showToast(@StringRes message: Int) {
        _toastEvent.update {
            it.copy(messageRes = message)
        }
    }

    fun showToast(message: String) {
        _toastEvent.update {
            it.copy(message = message)
        }
    }

    fun clearToast() {
        _toastEvent.update {
            ToastEvent(
                bottomPadding = it.bottomPadding,
                message = "",
                messageRes = Int.MIN_VALUE
            )
        }
    }
}

data class ToastEvent(
    @StringRes val messageRes: Int = Int.MIN_VALUE,
    val message: String = "",
    val bottomPadding: Int = 0
) {
    fun isEmptyMessage(): Boolean {
        return message.isEmpty() && messageRes == Int.MIN_VALUE
    }
}