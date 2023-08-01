package com.fone.filmone.ui.login

import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.login.SNSLoginUtil
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.data.datamodel.common.network.ErrorCode
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.usecase.SocialSignInUseCase
import com.fone.filmone.ui.common.base.BaseViewModel
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.navigation.NavDestinationState
import com.fone.filmone.ui.signup.model.SignUpVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socialSignInUseCase: SocialSignInUseCase
) : BaseViewModel() {

    val localSnsLoginUtil: SNSLoginUtil = SNSLoginUtil(object : SNSLoginUtil.LoginCallback {
        override fun onSuccess(
            token: String,
            email: String,
            loginType: LoginType
        ) {
            signIn(token, email, loginType)
        }

        override fun onFail(message: String) {
            LogUtil.e("Fail :: $message")
        }

        override fun onCancel() {
            LogUtil.w("cancel sns login")
        }
    })

    fun signIn(
        accessToken: String,
        email: String,
        loginType: LoginType
    ) = viewModelScope.launch {
        socialSignInUseCase(accessToken, email, loginType)
            .onSuccess { response ->
                if (response != null) {
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = FOneDestinations.Main.route,
                            isPopAll = true
                        )
                    )
                }
            }.onFail {
                if (it.errorCode == ErrorCode.NotFoundUserException.name) {
                    FOneNavigator.navigateTo(
                        NavDestinationState(
                            route = FOneDestinations.SignUpFirst.getRouteWithArg(
                                SignUpVo(
                                    accessToken = accessToken,
                                    email = email,
                                    loginType = loginType
                                )
                            )
                        )
                    )
                } else {
                    showToast(it.message)
                }
            }
    }
}
