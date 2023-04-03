package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.BuildConfig
import com.fone.filmone.core.login.model.SnsLoginType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginUtil private constructor(
    private val onSuccess: (token: String, snsLoginType: SnsLoginType) -> Unit,
    private val onFail: (message: String) -> Unit,
    private val onCancel: () -> Unit
) {
    fun login(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(
                context = context,
                callback = { token, error ->
                    handleCallback(context, token, error)
                }
            )
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = { token, error ->
                    handleCallback(context, token, error)
                }
            )
        }
    }

    private fun handleCallback(
        context: Context,
        oAuthToken: OAuthToken?,
        throwable: Throwable?
    ) {
        if (throwable != null) {
            if (throwable is AuthError && throwable.reason == AuthErrorCause.Unknown) {
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = { token, error -> handleCallback(context, token, error) }
                )
            } else {
                val reason: String = when (throwable) {
                    is ClientError -> "${throwable.message} (${throwable.reason.name})"
                    is AuthError -> if (BuildConfig.DEBUG) {
                        "${throwable.message} (${throwable.reason.name})"
                    } else {
                        ""
                    }
                    else -> ""
                }
                if (throwable is ClientError && throwable.reason == ClientErrorCause.Cancelled) {
                    onCancel.invoke()
                } else {
                    onFail(reason)
                }
            }
        } else if (oAuthToken != null) {
            onSuccess.invoke(oAuthToken.accessToken, SnsLoginType.Kakao)
        }
    }

    companion object {
        private var instance: KakaoLoginUtil? = null

        fun getInstance(
            onSuccess: (token: String, snsLoginType: SnsLoginType) -> Unit,
            onFail: (message: String) -> Unit,
            onCancel: () -> Unit
        ) = instance ?: synchronized(this) {
            instance ?: KakaoLoginUtil(onSuccess, onFail, onCancel).also {
                instance = it
            }
        }
    }
}