package com.fone.filmone.ui.email.login

import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.EmailSignInUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailLoginViewModel @Inject constructor(
    private val emailSignInUseCase: EmailSignInUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(EmailLoginViewModelState())

    val uiState = viewModelState
        .map(EmailLoginViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun signIn() = viewModelScope.launch {
        val email: String = viewModelState.value.email
        val password: String = viewModelState.value.password

        emailSignInUseCase(email, password)
            .onSuccess { response ->
                if (response != null) {
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = FOneDestinations.Main.route,
                            isPopAll = true
                        )
                    )
                }
            }.onFail {
                showToast(it.message)
            }
    }

    fun updateEmail(email: String) {
        viewModelState.update {
            it.copy(email = email)
        }

        updateButtonState()
    }

    fun updatePassword(password: String) {
        viewModelState.update {
            it.copy(password = password)
        }

        updateButtonState()
    }

    private fun updateButtonState() {
        val state = viewModelState.value

        val enable = PatternUtil.isValidEmail(state.email) && state.password.isNotEmpty()

        viewModelState.update {
            it.copy(isButtonEnable = enable)
        }
    }
}

private data class EmailLoginViewModelState(
    val email: String = "",
    val password: String = "",
    val isButtonEnable: Boolean = false,
) {
    fun toUiState(): EmailLoginUiState {
        return EmailLoginUiState(
            email = email,
            password = password,
            isButtonEnable = isButtonEnable
        )
    }
}

data class EmailLoginUiState(
    val email: String,
    val password: String,
    val isButtonEnable: Boolean,
)
