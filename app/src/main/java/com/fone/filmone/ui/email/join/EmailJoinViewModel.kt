package com.fone.filmone.ui.email.join

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.ValidateEmailUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailJoinViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(EmailJoinViewModelState())

    val uiState = viewModelState
        .map(EmailJoinViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun updateName(name: String) {
        viewModelState.update {
            it.copy(name = name)
        }

        updateNextButtonEnable()
    }

    fun updateEmail(email: String) {
        viewModelState.update {
            it.copy(
                email = email,
                emailErrorType = if (isValidateEmail(email)) {
                    null
                } else {
                    EmailErrorType.NOT_EMAIL_TYPE
                },
            )
        }
    }

    fun checkDuplicateEmail(code: String = "") = viewModelScope.launch {
        val email = uiState.value.email
        validateEmailUseCase(code, email)
            .onSuccess { response ->
                if (response == null) {
                    showToast(R.string.toast_empty_data)
                    return@onSuccess
                }

                viewModelState.update {
                    it.copy(
                        isEmailChecked = true,
                        isEmailDuplicated = false
                    )
                }

                updateNextButtonEnable()
            }.onFail {
                viewModelState.update {
                    it.copy(
                        isEmailDuplicated = true
                    )
                }

                showToast(it.message)
            }
    }

    private fun isValidateEmail(email: String): Boolean {
        return PatternUtil.isValidEmail(email)
    }

    fun updatePassword(password: String) {
        viewModelState.update {
            it.copy(
                password = password,
                passwordErrorType = if (PatternUtil.isValidPassword(password = password)) {
                    null
                } else {
                    PasswordErrorType.INVALID_TYPE
                }
            )
        }

        updateNextButtonEnable()
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        viewModelState.update {
            it.copy(
                confirmedPassword = confirmedPassword,
                confirmedPasswordErrorType = if (uiState.value.password != confirmedPassword) {
                    PasswordErrorType.NOT_MATCH
                } else {
                    null
                }
            )
        }

        updateNextButtonEnable()
    }

    fun updatePasswordVisible() {
        viewModelState.update {
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }

    fun updateConfirmedPasswordVisible() {
        viewModelState.update {
            it.copy(isConfirmedPasswordVisible = it.isConfirmedPasswordVisible.not())
        }
    }

    private fun updateNextButtonEnable() {
        val state = viewModelState.value
        val enable = state.name.isNotEmpty() && state.isEmailChecked &&
            state.password.isNotEmpty() && state.confirmedPassword.isNotEmpty() &&
            state.password == state.confirmedPassword &&
            PatternUtil.isValidPassword(state.password)

        viewModelState.update {
            it.copy(isNextButtonEnable = enable)
        }
    }
}

private data class EmailJoinViewModelState(
    val name: String = "",
    val email: String = "",
    val isEmailChecked: Boolean = false,
    val isEmailDuplicated: Boolean = false,
    val emailErrorType: EmailErrorType? = null,
    val password: String = "",
    val confirmedPassword: String = "",
    val passwordErrorType: PasswordErrorType? = null,
    val confirmedPasswordErrorType: PasswordErrorType? = null,
    val token: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmedPasswordVisible: Boolean = false,
    val isNextButtonEnable: Boolean = false
) {
    fun toUiState(): EmailJoinUiState {
        return EmailJoinUiState(
            name = name,
            email = email,
            isEmailChecked = isEmailChecked,
            isEmailDuplicated = isEmailDuplicated,
            emailErrorType = emailErrorType,
            password = password,
            confirmedPassword = confirmedPassword,
            passwordErrorType = passwordErrorType,
            confirmedPasswordErrorType = confirmedPasswordErrorType,
            token = token,
            isPasswordVisible = isPasswordVisible,
            isConfirmedPasswordVisible = isConfirmedPasswordVisible,
            isNextButtonEnable = isNextButtonEnable,
        )
    }
}

data class EmailJoinUiState(
    val name: String,
    val email: String,
    val isEmailChecked: Boolean,
    val isEmailDuplicated: Boolean,
    val emailErrorType: EmailErrorType?,
    val password: String,
    val confirmedPassword: String,
    val passwordErrorType: PasswordErrorType?,
    val confirmedPasswordErrorType: PasswordErrorType?,
    val token: String,
    val isPasswordVisible: Boolean,
    val isConfirmedPasswordVisible: Boolean,
    val isNextButtonEnable: Boolean
)
