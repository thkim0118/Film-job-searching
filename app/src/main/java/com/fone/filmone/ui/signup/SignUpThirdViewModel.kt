package com.fone.filmone.ui.signup

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.util.VerificationTimer
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.RequestPhoneVerificationUseCase
import com.fone.filmone.domain.usecase.VerifySmsCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpThirdViewModel @Inject constructor(
    private val requestPhoneVerificationUseCase: RequestPhoneVerificationUseCase,
    private val verifySmsCodeUseCase: VerifySmsCodeUseCase
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
    private val retransmitTimer = object : CountDownTimer(1 * 60 * 1000, 1000) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            updateRetransmitPossible(true)
        }
    }

    private var requestCount: Int = 0

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

    private fun updateRetransmitPossible(isPossible: Boolean) {
        _uiState.update {
            it.copy(isTransmitPossible = isPossible)
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
        if (isTransmitImpossible()) {
            return@launch
        }

        requestPhoneVerificationUseCase.invoke(
            "+82${uiState.value.phoneNumber.drop(1)}"
        ).onSuccess {
            _uiState.update {
                it.copy(phoneVerificationState = PhoneVerificationState.Retransmit)
            }

            verificationTimer.startVerificationTimer()
            // TODO "인증번호를 전송하였습니다" 문구 토스트
        }
    }

    private fun isTransmitImpossible(): Boolean {
        val isRetransmitPossible = uiState.value.isTransmitPossible

        if (isRetransmitPossible) {
            requestCount += 1
        }

        if (requestCount == 3) {
            retransmitTimer.start()
            updateRetransmitPossible(false)
        }

        if (requestCount > 4 && isRetransmitPossible.not()) {
            // TODO "너무 많은 요청을 하셨습니다. 5분 후 다시 시도해 주세요." 토스트.
            return true
        }

        requestCount %= 3
        return false
    }

    fun checkVerificationCode(code: String) = viewModelScope.launch {
        if (uiState.value.verificationTime == "00:00") {
            // TODO 인증 시간 만료에 대한 예외처리.
            return@launch
        }

        verifySmsCodeUseCase.invoke(code).onSuccess { isVerify ->
            if (isVerify) {
                updateDialogState(SignUpThirdDialogState.VerificationComplete)
                updatePhoneNumberVerification()
                verificationTimer.finishVerificationTimer()
            } else {
                // TODO "올바른 인증번호를 입력해주세요" 문구 토스트
            }
        }
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
        retransmitTimer.cancel()
    }
}

data class SignUpThirdUiState(
    val phoneNumber: String = "",
    val phoneVerificationState: PhoneVerificationState = PhoneVerificationState.ShouldVerify,
    val verificationCode: String = "",
    val verificationTime: String = "3:00",
    val isTransmitPossible: Boolean = true,
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
