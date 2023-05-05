package com.fone.filmone.ui.main

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.LogoutUseCase
import com.fone.filmone.domain.usecase.SignOutUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(MainViewModelState())

    val uiState = viewModelState
        .map(MainViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    suspend fun logout() = viewModelScope.launch {
        logoutUseCase()
            .onSuccess {
                FOneNavigator.navigateTo(
                    navDestinationState = NavDestinationState(
                        route = FOneDestinations.Login.route,
                        isPopAll = true
                    )
                )
            }.onFail {
                showToast(R.string.toast_empty_data)
            }
    }

    suspend fun signOut() = viewModelScope.launch {
        signOutUseCase()
            .onSuccess {
                viewModelState.update {
                    it.copy(
                        mainDialogState = MainDialogState.WithdrawalComplete
                    )
                }
            }.onFail {
                showToast(R.string.toast_empty_data)
            }
    }

    fun clearDialog() {
        viewModelState.update {
            it.copy(mainDialogState = MainDialogState.Clear)
        }
    }

    fun showFloatingDimBackground() {
        viewModelState.update {
            it.copy(isFloatingClick = true)
        }
    }

    fun hideFloatingDimBackground() {
        viewModelState.update {
            it.copy(isFloatingClick = false)
        }
    }
}

private data class MainViewModelState(
    val mainDialogState: MainDialogState = MainDialogState.Clear,
    val isFloatingClick: Boolean = false,
) {
    fun toUiState() = MainUiState(
        mainDialogState = mainDialogState,
        isFloatingClick = isFloatingClick
    )
}

data class MainUiState(
    val mainDialogState: MainDialogState,
    val isFloatingClick: Boolean,
)

sealed interface MainDialogState {
    object Clear : MainDialogState
    object WithdrawalComplete : MainDialogState
}
