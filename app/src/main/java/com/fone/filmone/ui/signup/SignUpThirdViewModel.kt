package com.fone.filmone.ui.signup

import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.core.util.VerificationTimer
import com.fone.filmone.domain.model.common.*
import com.fone.filmone.domain.usecase.RequestPhoneVerificationUseCase
import com.fone.filmone.domain.usecase.SignUpUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.domain.usecase.VerifySmsCodeUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.model.SignUpVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SignUpThirdViewModel @Inject constructor(
    private val requestPhoneVerificationUseCase: RequestPhoneVerificationUseCase,
    private val verifySmsCodeUseCase: VerifySmsCodeUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {
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
    private var profileUrl: String = ""

    fun signUp(signUpVo: SignUpVo) = viewModelScope.launch {
        val agreeStates = uiState.value.agreeState

        if (profileUrl.isEmpty() && signUpVo.encodingImage.isNotEmpty()) {
            val result = uploadImageUseCase.invoke(signUpVo.encodingImage)
            if (result.isFail()) {
                showToast(R.string.toast_profile_register_fail)
                return@launch
            }

            val response = result.getOrNull() ?: run {
                return@launch
            }
            profileUrl = response.imageUrl
        }

        signUpUseCase.invoke(
            signUpVo = signUpVo.copy(
                phoneNumber = PhoneNumberUtils.formatNumber(
                    uiState.value.phoneNumber,
                    "KR"
                ),
                agreeToTermsOfServiceTermsOfUse = agreeStates.contains(AgreeState.Term),
                agreeToPersonalInformation = agreeStates.contains(AgreeState.Privacy),
                isReceiveMarketing = agreeStates.contains(AgreeState.Marketing),
                profileUrl = profileUrl
            )
        ).onSuccess {
            FOneNavigator.navigateTo(
                FOneDestinations.SignUpComplete.getRouteWithArg(
                    accessToken = signUpVo.accessToken,
                    email = signUpVo.email,
                    socialLoginType = signUpVo.socialLoginType
                )
            )
        }.onFail {
            updateDialogState(SignUpThirdDialogState.SignUpFail)
        }
    }

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

    fun updateAllAgreeState() {
        val isAgreeAll = uiState.value.isTermAllAgree.not()

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

            verificationTimer.finishVerificationTimer()
            verificationTimer.startVerificationTimer()
            showToast(R.string.toast_request_verification_code)
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
            showToast(R.string.toast_request_verification_code_too_much)
            return true
        }

        requestCount %= 3
        return false
    }

    fun checkVerificationCode(code: String) = viewModelScope.launch {
        if (uiState.value.verificationTime == "00:00") {
            showToast(R.string.toast_verification_time_is_expired)
            return@launch
        }

        verifySmsCodeUseCase.invoke(code).onSuccess { isVerify ->
            if (isVerify) {
                updateDialogState(SignUpThirdDialogState.VerificationComplete)
                updatePhoneNumberVerification()
                verificationTimer.finishVerificationTimer()
            } else {
                showToast(R.string.toast_verification_code_is_error)
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
