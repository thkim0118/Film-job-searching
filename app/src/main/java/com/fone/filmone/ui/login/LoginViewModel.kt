package com.fone.filmone.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fone.filmone.core.login.SNSLoginUtil
import com.fone.filmone.core.util.LogUtil
import com.fone.filmone.domain.model.common.onFail
import com.fone.filmone.domain.model.common.onSuccess
import com.fone.filmone.domain.model.signup.SocialLoginType
import com.fone.filmone.domain.usecase.SignInUseCase
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import com.fone.filmone.ui.signup.model.SignUpVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    val localSnsLoginUtil: SNSLoginUtil = SNSLoginUtil(object : SNSLoginUtil.LoginCallback {
        override fun onSuccess(
            token: String,
            email: String,
            socialLoginType: SocialLoginType
        ) {
            LogUtil.d("$socialLoginType, $token")
            signIn(token, email, socialLoginType.name)
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
        socialLoginType: String
    ) = viewModelScope.launch {
        signInUseCase.invoke(accessToken, email, socialLoginType)
            .onSuccess {
                FOneNavigator.navigateTo(destinations = FOneDestinations.Main, isPopAll = true)
            }.onFail {
                FOneNavigator.navigateTo(
                    FOneDestinations.SignUpFirst.getRouteWithArg(
                        SignUpVo(
                            accessToken = accessToken,
                            email = email,
                            socialLoginType = socialLoginType
                        )
                    )
                )
            }
    }
}
