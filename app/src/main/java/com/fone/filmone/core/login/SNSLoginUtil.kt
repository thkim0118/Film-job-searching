package com.fone.filmone.core.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fone.filmone.domain.model.signup.SocialLoginType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class SNSLoginUtil private constructor(
    private val loginCallback: LoginCallback,
) {

    fun login(
        context: Context,
        socialLoginType: SocialLoginType,
        launcher: ActivityResultLauncher<Intent>? = null,
    ) {
        when (socialLoginType) {
            SocialLoginType.APPLE -> {
                // TODO Firebase Project 에 연결 필요.
                AppleLoginImpl(loginCallback).login(context)
            }
            SocialLoginType.GOOGLE -> {
                launcher?.let {
                    GoogleLoginImpl(loginCallback, launcher).login(context)
                } ?: return
            }
            SocialLoginType.KAKAO -> {
                KakaoLoginImpl(loginCallback).login(context)
            }
            SocialLoginType.NAVER -> {
                NaverLoginImpl(loginCallback).login(context)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> handleResult(data: T, socialLoginType: SocialLoginType) {
        when (socialLoginType) {
            SocialLoginType.GOOGLE -> {
                val accessToken =
                    GoogleLoginImpl.getAccessToken(data as? Task<GoogleSignInAccount> ?: return)

                if (accessToken != null) {
                    loginCallback.onSuccess(accessToken, socialLoginType)
                } else {
                    loginCallback.onFail("No AccessToken")
                }
            }
            else -> Unit
        }
    }

    interface LoginCallback {
        fun onSuccess(token: String, socialLoginType: SocialLoginType)
        fun onFail(message: String)
        fun onCancel()
    }

    companion object {
        @Volatile
        private var instance: SNSLoginUtil? = null

        fun getInstance(): SNSLoginUtil? = instance

        fun getInstance(loginCallback: LoginCallback): SNSLoginUtil {
            return instance ?: synchronized(this) {
                instance ?: SNSLoginUtil(loginCallback).also {
                    instance = it
                }
            }
        }
    }
}