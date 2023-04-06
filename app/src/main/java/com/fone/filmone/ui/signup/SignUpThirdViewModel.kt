package com.fone.filmone.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.util.VerificationTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpThirdViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpThirdUiState())
    val uiState: StateFlow<SignUpThirdUiState> = _uiState.asStateFlow()

    private val _dialogState =
        MutableStateFlow<SignUpThirdDialogState>(SignUpThirdDialogState.Clear)
    val dialogState: StateFlow<SignUpThirdDialogState> = _dialogState.asStateFlow()

    private val verificationTimer = VerificationTimer(
        onChangedListener = { time ->
            updateVerificationTime(time)
        }
    )

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }

    private fun updateVerificationTime(time: String) {
        _uiState.update {
            it.copy(verificationTime = time)
        }
    }

    fun updateAgreeState(agreeState: AgreeState) {
        _uiState.update { uiState ->
            val isAgree = uiState.agreeState.contains(agreeState).not()

            uiState.copy(
                agreeState = if (isAgree) {
                    uiState.agreeState + agreeState
                } else {
                    uiState.agreeState.filterNot { it == agreeState }.toSet()
                }
            )
        }

        updateTermAllAgree(_uiState.value.agreeState.size == AgreeState.values().size)
    }

    fun updateAllAgreeState(isAgreeAll: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(
                agreeState = if (isAgreeAll) {
                    AgreeState.values().toSet()
                } else {
                    setOf()
                },
                isTermAllAgree = isAgreeAll,
                isRequiredTemAllAgree = isAgreeAll
            )
        }
    }

    private fun updateTermAllAgree(isAgreeAll: Boolean) {
        _uiState.update { uiState ->
            val requiredAgreeSize = AgreeState.values().filter { it.isRequired }.size
            val selectedRequiredAgreeSize = uiState.agreeState.filter { it.isRequired }.size

            uiState.copy(
                isTermAllAgree = isAgreeAll,
                isRequiredTemAllAgree = selectedRequiredAgreeSize == requiredAgreeSize
            )
        }
    }

    fun transmitVerificationCode() = viewModelScope.launch {
        // TODO Phone 인증 api 호출
        // if success
        _uiState.update {
            it.copy(phoneVerificationState = PhoneVerificationState.Retransmit,)
        }

        verificationTimer.startVerificationTimer()
    }

    fun checkVerificationCode() = viewModelScope.launch {
        if (uiState.value.verificationTime == "0:00") {
            // TODO 인증 시간 만료에 대한 예외처리.
            return@launch
        }

        // TODO 인증번호 검증 api 호출
        // if success
        updateDialogState(SignUpThirdDialogState.VerificationComplete)
        updatePhoneNumberVerification()
        verificationTimer.finishVerificationTimer()
    }

    fun clearDialogState() {
        _dialogState.value = SignUpThirdDialogState.Clear
    }

    private fun updateDialogState(dialogState: SignUpThirdDialogState) {
        _dialogState.value = dialogState
    }

    private fun updatePhoneNumberVerification() {
        _uiState.update {
            it.copy(phoneVerificationState = PhoneVerificationState.Complete)
        }
    }

    override fun onCleared() {
        super.onCleared()

        verificationTimer.finishVerificationTimer()
    }
}

data class SignUpThirdUiState(
    val phoneNumber: String = "",
    val phoneVerificationState: PhoneVerificationState = PhoneVerificationState.ShouldVerify,
    val verificationCode: String = "",
    val verificationTime: String = "3:00",
    val agreeState: Set<AgreeState> = setOf(),
    val isTermAllAgree: Boolean = false,
    val isRequiredTemAllAgree: Boolean = false,
)

sealed interface PhoneVerificationState {
    object Complete : PhoneVerificationState
    object ShouldVerify : PhoneVerificationState
    object Retransmit : PhoneVerificationState
}

enum class AgreeState(val isRequired: Boolean) {
    Term(true),
    Privacy(true),
    Marketing(false)
}

sealed interface SignUpThirdDialogState {
    object VerificationComplete : SignUpThirdDialogState
    object SignUpFail : SignUpThirdDialogState
    object Clear : SignUpThirdDialogState
}
