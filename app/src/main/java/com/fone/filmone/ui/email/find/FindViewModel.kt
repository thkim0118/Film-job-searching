package com.fone.filmone.ui.email.find

import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import androidx.lifecycle.viewModelScope
import com.fone.filmone.BuildConfig
import com.fone.filmone.R
import com.fone.filmone.core.util.timer.VerificationTimer
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.ChangePasswordUseCase
import com.fone.filmone.domain.usecase.FindIdUseCase
import com.fone.filmone.domain.usecase.FindPasswordUseCase
import com.fone.filmone.domain.usecase.RequestPhoneVerificationUseCase
import com.fone.filmone.domain.usecase.VerifySmsCodeUseCase
import com.fone.filmone.ui.common.ToastDuration
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.common.phone.PhoneVerificationState
import com.fone.filmone.ui.email.find.model.FindScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindViewModel @Inject constructor(
    private val requestPhoneVerificationUseCase: RequestPhoneVerificationUseCase,
    private val verifySmsCodeUseCase: VerifySmsCodeUseCase,
    private val findIdUseCase: FindIdUseCase,
    private val findPasswordUseCase: FindPasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : BaseViewModel() {
    private val viewModelState = MutableStateFlow(FindViewModelState())

    val uiState = viewModelState
        .map(FindViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private val _dialogState = MutableStateFlow<FindDialogState>(FindDialogState.Clear)
    val dialogState: StateFlow<FindDialogState> = _dialogState.asStateFlow()

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

    private fun updateVerificationTime(time: String) {
        viewModelState.update {
            it.copy(verificationTime = time)
        }
    }

    fun resetData() {
        viewModelState.value = FindViewModelState(
            phoneNumber = "",
            phoneVerificationState = PhoneVerificationState.ShouldVerify,
            verificationTime = "3:00",
            isTransmitPossible = true,
            goLoginButtonEnable = false,
            changePasswordButtonEnable = false,
            newPassword = "",
            confirmedPassword = "",
            isNewPasswordVisible = false,
            isConfirmedPasswordVisible = false
        )

        verificationTimer.finishVerificationTimer()
    }

    fun updatePhoneNumber(phoneNumber: String) {
        viewModelState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }

    fun transmitVerificationCode() = viewModelScope.launch {
        if (isTransmitImpossible()) {
            return@launch
        }

        requestPhoneVerificationUseCase(uiState.value.phoneNumber).onSuccess {
            viewModelState.update {
                it.copy(phoneVerificationState = PhoneVerificationState.Retransmit)
            }

            verificationTimer.finishVerificationTimer()
            verificationTimer.startVerificationTimer()
            showToast(R.string.toast_request_verification_code)

            if (BuildConfig.DEBUG) {
                showToast(it?.debugCode ?: "ERROR", ToastDuration.SEC_5)
            }
        }
    }

    private fun isTransmitImpossible(): Boolean {
        val isRetransmitPossible = viewModelState.value.isTransmitPossible

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

    private fun updateRetransmitPossible(isPossible: Boolean) {
        viewModelState.update {
            it.copy(isTransmitPossible = isPossible)
        }
    }

    fun checkVerificationCode(code: String, findScreenType: FindScreenType) =
        viewModelScope.launch {
            if (uiState.value.verificationTime == "00:00") {
                showToast(R.string.toast_verification_time_is_expired)
                return@launch
            }

            verifySmsCodeUseCase(code).onSuccess { isVerify ->
                if (isVerify == null) {
                    return@onSuccess
                }

                if (isVerify) {
                    findInfo(findScreenType, code.toInt())
                    updatePhoneNumberVerification()
                    verificationTimer.finishVerificationTimer()
                } else {
                    showToast(R.string.toast_verification_code_is_error)
                }
            }
        }

    private fun findInfo(
        findScreenType: FindScreenType,
        code: Int,
    ) = viewModelScope.launch {
        when (findScreenType) {
            FindScreenType.FIND_ID -> {
                findId(code)
            }

            FindScreenType.FIND_PASSWORD -> {
                findPassword(code)
            }
        }
    }

    private fun findId(code: Int) = viewModelScope.launch {
        findIdUseCase(
            code = code,
            phoneNumber = PhoneNumberUtils.formatNumber(
                uiState.value.phoneNumber,
                "KR"
            )
        ).onSuccess { response ->
            if (response != null) {
                viewModelState.update {
                    it.copy(
                        findIdLoginType = it.findIdLoginType,
                        findIdEmail = it.findIdEmail
                    )
                }
            }
        }.onFail {
            showToast(it.message)
        }
    }

    private fun findPassword(code: Int) = viewModelScope.launch {
        findPasswordUseCase(
            code = code,
            phoneNumber = PhoneNumberUtils.formatNumber(
                uiState.value.phoneNumber,
                "KR"
            )
        ).onSuccess { response ->
            if (response?.token == null) {
                return@onSuccess
            }

            viewModelState.update {
                it.copy(findPasswordToken = response.token)
            }
        }.onFail {
            showToast(it.message)
        }
    }

    fun changePassword() = viewModelScope.launch {
        changePasswordUseCase(
            password = uiState.value.confirmedPassword,
            phoneNumber = PhoneNumberUtils.formatNumber(
                uiState.value.phoneNumber,
                "KR"
            ),
            token = uiState.value.findPasswordToken
        ).onSuccess {
            updateDialogState(FindDialogState.PasswordChangingComplete)
        }.onFail {
            showToast(it.message)
        }
    }

    private fun updatePhoneNumberVerification() {
        viewModelState.update {
            it.copy(phoneVerificationState = PhoneVerificationState.Complete)
        }
    }

    fun updateNewPassword(newPassword: String) {
        viewModelState.update {
            it.copy(newPassword = newPassword)
        }
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        viewModelState.update {
            it.copy(confirmedPassword = confirmedPassword)
        }
    }

    fun updateNewPasswordVisible() {
        viewModelState.update {
            it.copy(isNewPasswordVisible = it.isNewPasswordVisible.not())
        }
    }

    fun updateConfirmedPasswordVisible() {
        viewModelState.update {
            it.copy(isConfirmedPasswordVisible = it.isConfirmedPasswordVisible.not())
        }
    }

    private fun updateDialogState(dialogState: FindDialogState) {
        _dialogState.update { dialogState }
    }
}

private data class FindViewModelState(
    val phoneNumber: String = "",
    val phoneVerificationState: PhoneVerificationState = PhoneVerificationState.ShouldVerify,
    val verificationTime: String = "3:00",
    val isTransmitPossible: Boolean = true,
    val findIdLoginType: LoginType? = null,
    val findIdEmail: String = "",
    val findPasswordToken: String = "",
    val goLoginButtonEnable: Boolean = false,
    val changePasswordButtonEnable: Boolean = false,
    val newPassword: String = "",
    val confirmedPassword: String = "",
    val isNewPasswordVisible: Boolean = false,
    val isConfirmedPasswordVisible: Boolean = false
) {
    fun toUiState(): FindUiState {
        return FindUiState(
            phoneNumber = phoneNumber,
            phoneVerificationState = phoneVerificationState,
            verificationTime = verificationTime,
            isTransmitPossible = isTransmitPossible,
            findIdLoginType = findIdLoginType,
            findIdEmail = findIdEmail,
            findPasswordToken = findPasswordToken,
            goLoginButtonEnable = goLoginButtonEnable,
            changePasswordButtonEnable = changePasswordButtonEnable,
            newPassword = newPassword,
            confirmedPassword = confirmedPassword,
            isNewPasswordVisible = isNewPasswordVisible,
            isConfirmedPasswordVisible = isConfirmedPasswordVisible
        )
    }
}

data class FindUiState(
    val phoneNumber: String,
    val phoneVerificationState: PhoneVerificationState,
    val verificationTime: String,
    val isTransmitPossible: Boolean,
    val findIdLoginType: LoginType?,
    val findIdEmail: String,
    val findPasswordToken: String,
    val goLoginButtonEnable: Boolean,
    val changePasswordButtonEnable: Boolean,
    val newPassword: String,
    val confirmedPassword: String,
    val isNewPasswordVisible: Boolean,
    val isConfirmedPasswordVisible: Boolean
)

sealed interface FindDialogState {
    object PasswordChangingComplete : FindDialogState
    object Clear : FindDialogState
}
