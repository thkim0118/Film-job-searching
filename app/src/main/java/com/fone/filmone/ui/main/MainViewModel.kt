package com.fone.filmone.ui.main

import androidx.lifecycle.viewModelScope
import com.fone.filmone.R
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.LogoutUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

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
}