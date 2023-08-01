package com.fone.filmone.ui.signup

import androidx.lifecycle.viewModelScope
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.EmailSignInUseCase
import com.fone.filmone.domain.usecase.SocialSignInUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(
    private val socialSignInUseCase: SocialSignInUseCase,
    private val emailSignInUseCase: EmailSignInUseCase
) : BaseViewModel() {

    fun signIn(
        accessToken: String,
        email: String,
        password: String?,
        loginType: String
    ) = viewModelScope.launch {
        val dataResult = if (loginType == LoginType.PASSWORD.name && password != null) {
            emailSignInUseCase(email, password)
        } else {
            socialSignInUseCase(
                accessToken = accessToken,
                email = email,
                socialLoginType = LoginType.values().find { it.name == loginType } ?: return@launch
            )
        }

        dataResult
            .onSuccess {
                FOneNavigator.navigateTo(NavDestinationState(route = FOneDestinations.Main.route))
            }.onFail {
                showToast(it.message)
            }
    }
}
