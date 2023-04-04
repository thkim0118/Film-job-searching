package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.BuildConfig
import com.fone.filmone.domain.model.signup.SocialLoginType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginImpl (
    override val loginCallback: SNSLoginUtil.LoginCallback
) : SnsLogin {
    override fun login(context: Context) {
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
                    loginCallback.onCancel()
                } else {
                    loginCallback.onFail(reason)
                }
            }
        } else if (oAuthToken != null) {
            loginCallback.onSuccess(oAuthToken.accessToken, SocialLoginType.KAKAO)
        }
    }
}