package com.fone.filmone.ui.inquiry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.inquiry.InquiryType
import com.fone.filmone.domain.model.inquiry.InquiryVo
import com.fone.filmone.domain.usecase.SubmitInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val submitInquiryUseCase: SubmitInquiryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(InquiryUiState())
    val uiState: StateFlow<InquiryUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updateInquiryType(inquiryType: InquiryType) {
        _uiState.update {
            it.copy(inquiryType = inquiryType)
        }
    }

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun updateDescription(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }

    fun updatePrivacyInformation() {
        _uiState.update {
            it.copy(isAgreePersonalInformation = it.isAgreePersonalInformation.not())
        }
    }

    fun submitInquiry() = viewModelScope.launch {
        // TODO Throttling
        submitInquiryUseCase.invoke(
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

        }.onFail {

        }
    }
}

data class InquiryUiState(
    val email: String = "",
    val inquiryType: InquiryType? = null,
    val title: String = "",
    val description: String = "",
    val isAgreePersonalInformation: Boolean = false
)