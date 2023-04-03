package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.core.LogUtil
import com.fone.filmone.core.login.model.SnsLoginType
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthErrorCode
import com.navercorp.nid.oauth.OAuthLoginCallback

class NaverLoginImpl (
    override val loginCallback: SNSLoginUtil.LoginCallback
) : SnsLogin {
    override fun login(context: Context) {
        NaverIdLoginSDK.authenticate(context, NaverOAuthLoginCallback())
    }

    private inner class NaverOAuthLoginCallback : OAuthLoginCallback {
        override fun onError(errorCode: Int, message: String) {
            handleError(errorCode, message)
        }

        override fun onFailure(httpStatus: Int, message: String) {
            loginCallback.onCancel()
        }

        override fun onSuccess() {
            val accessToken = NaverIdLoginSDK.getAccessToken() ?: run {
                loginCallback.onFail("No Data")
                return
            }

            LogUtil.d("${SnsLoginType.Naver} $accessToken")
            loginCallback.onSuccess(accessToken, SnsLoginType.Naver)
        }

        private fun handleError(errorCode: Int, message: String) {
            val code = NaverIdLoginSDK.getLastErrorCode().code

            if (code == NidOAuthErrorCode.CLIENT_USER_CANCEL.code) {
                loginCallback.onCancel()
            } else {
                if (errorCode == -1) {
                    loginCallback.onCancel()
                } else {
                    loginCallback.onFail("errorCode: $errorCode, message: $message")
                }
            }
        }
    }
}