package com.fone.filmone.ui.signup

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.core.util.PatternUtil
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.request.imageupload.UploadingImage
import com.fone.filmone.domain.model.common.getOrNull
import com.fone.filmone.domain.model.common.isFail
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.CheckNicknameDuplicationUseCase
import com.fone.filmone.domain.usecase.UploadImageUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.signup.model.SignUpVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpSecondViewModel @Inject constructor(
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SignUpSecondUiState())
    val uiState: StateFlow<SignUpSecondUiState> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow<SignUpDialogState>(SignUpDialogState.Clear)
    val dialogState: StateFlow<SignUpDialogState> = _dialogState.asStateFlow()

    fun updateNickname(nickname: String) {
        if (nickname == _uiState.value.nickname) return

        _uiState.update {
            it.copy(nickname = nickname, isNicknameDuplicated = false, isNicknameChecked = false)
        }
    }

    fun updateBirthDay(birthday: String) {
        _uiState.update {
            it.copy(birthday = birthday)
        }

        updateBirthDayChecked(birthday)
    }

    fun updateProfileUploadState() {
        _uiState.update {
            it.copy(isProfileUploading = true)
        }
    }

    fun updateDialog(dialogState: SignUpDialogState) {
        _dialogState.value = dialogState
    }

    fun clearDialog() {
        _dialogState.value = SignUpDialogState.Clear
    }

    fun updateProfileImage(profileEncodedString: String) = viewModelScope.launch {
        if (profileEncodedString.isNotEmpty()) {
            val result = uploadImageUseCase(
                listOf(
                    UploadingImage(
                        imageData = profileEncodedString,
                        resource = UploadImageUseCase.userProfileResource,
                        stageVariables = UploadImageUseCase.stageVariables
                    )
                )
            )
            if (result.isFail()) {
                showToast(R.string.toast_profile_register_fail)
                return@launch
            }

            val response = result.getOrNull() ?: run {
                return@launch
            }

            _uiState.update {
                it.copy(
                    profileUrl = response.firstOrNull()?.imageUrl ?: "",
                    isProfileUploading = false
                )
            }
        } else {
            showToast(R.string.toast_profile_register_fail)
            return@launch
        }
    }

    private fun updateBirthDayChecked(birthday: String) {
        _uiState.update {
            it.copy(isBirthDayChecked = PatternUtil.isValidDate(birthday))
        }
    }

    fun updateGender(gender: Gender) {
        _uiState.update {
            it.copy(gender = gender)
        }
    }

    fun checkNicknameDuplication() = viewModelScope.launch {
        checkNicknameDuplicationUseCase(uiState.value.nickname)
            .onSuccess { isDuplicated ->
                if (isDuplicated == null) {
                    return@onSuccess
                }

                _uiState.update { uiState ->
                    uiState.copy(isNicknameChecked = true, isNicknameDuplicated = false)
                }
            }
            .onFail {
                if (it.errorCode == "DuplicateUserNicknameException") {
                    _uiState.update { uiState ->
                        uiState.copy(isNicknameChecked = false, isNicknameDuplicated = true)
                    }
                } else {
                    showToast(it.message)
                }
            }
    }

    fun updateSavedSignupVo(signUpVo: SignUpVo) {
        _uiState.update { uiState ->
            uiState.copy(
                nickname = signUpVo.nickname,
                birthday = signUpVo.birthday,
                gender = Gender.values().find {
                    it.name == signUpVo.gender
                },
                profileUrl = signUpVo.profileUrl,
                isBirthDayChecked = signUpVo.isBirthDayChecked,
                isNicknameChecked = signUpVo.isNicknameChecked,
                isNicknameDuplicated = signUpVo.isNicknameDuplicated,
                isProfileUploading = signUpVo.isProfileUploading
            )
        }
    }
}

data class SignUpSecondUiState(
    val nickname: String = "",
    val isNicknameChecked: Boolean = false,
    val isNicknameDuplicated: Boolean = false,
    val birthday: String = "",
    val isBirthDayChecked: Boolean = false,
    val profileUrl: String = "",
    val isProfileUploading: Boolean = false,
    val gender: Gender? = null,
)

sealed interface SignUpDialogState {
    object ProfileSetting : SignUpDialogState
    object Clear : SignUpDialogState
}
