package com.fone.filmone.ui.signup

import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.signup.Gender
import com.fone.filmone.domain.usecase.CheckNicknameDuplicationUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpSecondViewModel @Inject constructor(
    private val checkNicknameDuplicationUseCase: CheckNicknameDuplicationUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SignUpSecondUiState())
    val uiState: StateFlow<SignUpSecondUiState> = _uiState.asStateFlow()

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(nickname = nickname, isNicknameDuplicated = false)
        }

    }

    fun updateBirthDay(birthday: String) {
        _uiState.update {
            it.copy(birthday = birthday)
        }

        updateBirthDayChecked(birthday)
    }

    fun updateProfileEncoding() {
        _uiState.update {
            it.copy(isProfileEncoding = true)
        }
    }

    fun updateProfileImage(profileEncodedString: String) {
        _uiState.update {
            it.copy(
                encodingProfileImage = profileEncodedString,
                isProfileEncoding = false
            )
        }
    }

    private fun updateBirthDayChecked(birthday: String) {
        val birthDayPattern = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0\\d|[1-2]\\d|3[0-1])+$")
        _uiState.update {
            it.copy(isBirthDayChecked = birthDayPattern.matcher(birthday).matches())
        }
    }

    fun updateGender(gender: Gender) {
        _uiState.update {
            it.copy(gender = gender)
        }
    }

    fun checkNicknameDuplication() = viewModelScope.launch {
        // TODO Throttling.
        checkNicknameDuplicationUseCase(uiState.value.nickname)
            .onSuccess { isDuplicated ->
                if (isDuplicated) {
                    _uiState.update { uiState ->
                        uiState.copy(isNicknameChecked = false, isNicknameDuplicated = true)
                    }
                } else {
                    _uiState.update { uiState ->
                        uiState.copy(isNicknameChecked = true, isNicknameDuplicated = false)
                    }
                }
            }
    }
}

data class SignUpSecondUiState(
    val nickname: String = "",
    val isNicknameChecked: Boolean = false,
    val isNicknameDuplicated: Boolean = false,
    val birthday: String = "",
    val isBirthDayChecked: Boolean = false,
    val encodingProfileImage: String = "",
    val isProfileEncoding: Boolean = false,
    val gender: Gender? = null,
)