package com.fone.filmone.core.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class SNSLoginUtil(
    private val loginCallback: LoginCallback,
) {

    fun login(
        context: Context,
        loginType: LoginType,
        launcher: ActivityResultLauncher<Intent>? = null,
    ) {
        when (loginType) {
            LoginType.APPLE -> {
                // TODO Firebase Project 에 연결 필요.
                AppleLoginImpl(loginCallback).login(context)
            }
            LoginType.GOOGLE -> {
                launcher?.let {
                    GoogleLoginImpl(loginCallback, launcher).login(context)
                } ?: return
            }
            LoginType.KAKAO -> {
                KakaoLoginImpl(loginCallback).login(context)
            }
            LoginType.NAVER -> {
                NaverLoginImpl(loginCallback).login(context)
            }

            else -> {}
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> handleResult(data: T, loginType: LoginType) {
        when (loginType) {
            LoginType.GOOGLE -> {
                val loginInfo =
                    GoogleLoginImpl.getLoginInfo(data as? Task<GoogleSignInAccount> ?: return)

                if (loginInfo != null) {
                    val token = loginInfo.first
                    val email = loginInfo.second

                    loginCallback.onSuccess(
                        token,
                        email,
                        loginType
                    )
                } else {
                    loginCallback.onFail("No AccessToken")
                }
            }
            else -> Unit
        }
    }

    interface LoginCallback {
        fun onSuccess(
            token: String,
            email: String,
            loginType: LoginType
        )

        fun onFail(message: String)
        fun onCancel()
    }
}
