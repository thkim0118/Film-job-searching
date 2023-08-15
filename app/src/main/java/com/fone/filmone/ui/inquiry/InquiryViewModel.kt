package com.fone.filmone.ui.inquiry

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.inquiry.InquiryType
import com.fone.filmone.domain.model.inquiry.InquiryVo
import com.fone.filmone.domain.usecase.SubmitInquiryUseCase
import com.fone.filmone.ui.common.ToastDuration
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val submitInquiryUseCase: SubmitInquiryUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(InquiryUiState())
    val uiState: StateFlow<InquiryUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }

        updateButtonState()
    }

    fun updateInquiryType(inquiryType: InquiryType) {
        _uiState.update {
            it.copy(inquiryType = inquiryType)
        }

        updateButtonState()
    }

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }

        updateButtonState()
    }

    fun updateDescription(description: String) {
        _uiState.update {
            it.copy(description = description)
        }

        updateButtonState()
    }

    fun updatePrivacyInformation() {
        _uiState.update {
            it.copy(isAgreePersonalInformation = it.isAgreePersonalInformation.not())
        }

        updateButtonState()
    }

    fun submitInquiry() = viewModelScope.launch {
        // TODO Throttling
        submitInquiryUseCase(
            with(uiState.value) {
                InquiryVo(
                    email = email,
                    title = title,
                    description = description,
                    agreeToPersonalInformation = isAgreePersonalInformation,
                    type = inquiryType ?: return@launch
                )
            }
        ).onSuccess {
            _uiState.update {
                it.copy(isInquirySuccess = true)
            }
            showToastAndPopScreen(R.string.toast_inquiry_complete)
        }.onFail {
            _uiState.update {
                it.copy(isInquirySuccess = false)
            }
            showToast(R.string.toast_inquiry_fail)
        }
    }

    private fun showToastAndPopScreen(@StringRes messageRes: Int) = viewModelScope.launch {
        showToast(messageRes)
        delay(ToastDuration.SEC_2_5.milliseconds)
        _uiState.update {
            it.copy(popScreen = true)
        }
    }

    private fun updateButtonState() {
        val state = _uiState.value

        val enable = state.email.isNotEmpty() && PatternUtil.isValidEmail(state.email) &&
            state.inquiryType != null && state.title.isNotEmpty() &&
            state.description.isNotEmpty() && state.isAgreePersonalInformation

        _uiState.update {
            it.copy(buttonEnable = enable)
        }
    }
}

data class InquiryUiState(
    val email: String = "",
    val inquiryType: InquiryType? = null,
    val title: String = "",
    val description: String = "",
    val isAgreePersonalInformation: Boolean = false,
    val isInquirySuccess: Boolean = false,
    val popScreen: Boolean = false,
    val buttonEnable: Boolean = false,
)
