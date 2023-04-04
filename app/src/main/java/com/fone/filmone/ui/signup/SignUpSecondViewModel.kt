package com.fone.filmone.ui.signup

import androidx.lifecycle.ViewModel
import com.fone.filmone.domain.model.signup.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpSecondViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpSecondUiState())
    val uiState: StateFlow<SignUpSecondUiState> = _uiState.asStateFlow()

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(nickname = nickname)
        }

    }

    fun updateNicknameIsChecked() {
        _uiState.update {
            it.copy(isNicknameChecked = true)
        }
    }

    fun updateBirthDay(birthDay: String) {
        _uiState.update {
            it.copy(birthDay = birthDay)
        }
    }

    fun updateGender(gender: Gender) {
        _uiState.update {
            it.copy(gender = gender)
        }
    }
}

data class SignUpSecondUiState(
    val nickname: String = "",
    val isNicknameChecked: Boolean = false,
    val isNicknameDuplicated: Boolean = false,
    val birthDay: String = "",
    val gender: Gender? = null,
)