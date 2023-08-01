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

    fun isValidatePassword(): Boolean {
        val password = uiState.value.password
        val confirmedPassword = uiState.value.confirmedPassword

        if (password != confirmedPassword) {
            showToast("입력하신 비밀번호와 동일하지 않습니다.")
            return false
        }

        val regex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
        val isValid = regex.matches(password)

        return if (isValid) {
            true
        } else {
            showToast("8자 이상, 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
            false
        }
    }

    fun updatePassword(password: String) {
        viewModelState.update {
            it.copy(password = password)
        }

        updateNextButtonEnable()
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        viewModelState.update {
            it.copy(confirmedPassword = confirmedPassword)
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
                state.password == state.confirmedPassword

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
    val token: String,
    val isPasswordVisible: Boolean,
    val isConfirmedPasswordVisible: Boolean,
    val isNextButtonEnable: Boolean
)
