package com.fone.filmone.ui.common.base

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.fone.filmone.ui.common.ToastDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel : ViewModel() {

    private val _toastEvent = MutableStateFlow(ToastEvent())
    val toastEvent: StateFlow<ToastEvent> = _toastEvent.asStateFlow()

    fun showToast(@StringRes message: Int, toastDuration: ToastDuration = ToastDuration.SEC_2_5) {
        _toastEvent.update {
            it.copy(
                messageRes = message,
                toastDuration = toastDuration
            )
        }
    }

    fun showToast(message: String, toastDuration: ToastDuration = ToastDuration.SEC_2_5) {
        _toastEvent.update {
            it.copy(
                message = message,
                toastDuration = toastDuration
            )
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
    val toastDuration: ToastDuration = ToastDuration.SEC_2_5,
    val bottomPadding: Int = 0
) {
    fun isEmptyMessage(): Boolean {
        return message.isEmpty() && messageRes == Int.MIN_VALUE
    }

    @Composable
    fun getMessage(): String {
        return message.ifEmpty {
            stringResource(id = messageRes)
        }
    }
}
