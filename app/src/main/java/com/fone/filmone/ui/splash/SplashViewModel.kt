package com.fone.filmone.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(SplashViewModelState())

    val uiState = viewModelState
        .map(SplashViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            getUserInfoUseCase()
                .onSuccess {
                    viewModelState.update { viewModelState ->
                        viewModelState.copy(hasUserAccessToken = true)
                    }
                }
                .onFail {
                    viewModelState.update { viewModelState ->
                        viewModelState.copy(hasUserAccessToken = false)
                    }
                }
        }
    }
}

private data class SplashViewModelState(
    val hasUserAccessToken: Boolean? = null,
) {
    fun toUiState() = when (hasUserAccessToken) {
        true -> SplashUiState.AutoLoginUser
        false -> SplashUiState.NotLoginUser
        else -> SplashUiState.NotLoginUser
    }
}

sealed interface SplashUiState {
    object NotLoginUser : SplashUiState
    object AutoLoginUser : SplashUiState
}
