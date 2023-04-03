package com.fone.filmone.core.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fone.filmone.core.login.model.SnsLoginType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class SNSLoginUtil private constructor(
    private val loginCallback: LoginCallback,
) {

    fun login(
        context: Context,
        snsLoginType: SnsLoginType,
        launcher: ActivityResultLauncher<Intent>? = null,
    ) {
        when (snsLoginType) {
            SnsLoginType.Kakao -> {
                KakaoLoginImpl(loginCallback).login(context)
            }
            SnsLoginType.Naver -> {
                NaverLoginImpl(loginCallback).login(context)
            }
            SnsLoginType.Google -> {
                launcher?.let {
                    GoogleLoginImpl(loginCallback, launcher).login(context)
                } ?: return
            }
            SnsLoginType.Apple -> {
                AppleLoginImpl(loginCallback).login(context)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> handleResult(data: T, snsLoginType: SnsLoginType) {
        when (snsLoginType) {
            SnsLoginType.Google -> {
                val accessToken =
                    GoogleLoginImpl.getAccessToken(data as? Task<GoogleSignInAccount> ?: return)

                if (accessToken != null) {
                    loginCallback.onSuccess(accessToken, snsLoginType)
                } else {
                    loginCallback.onFail("No AccessToken")
                }
            }
            else -> Unit
        }
    }

    interface LoginCallback {
        fun onSuccess(token: String, snsLoginType: SnsLoginType)
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