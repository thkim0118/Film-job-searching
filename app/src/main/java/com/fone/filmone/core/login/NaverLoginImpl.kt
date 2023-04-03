package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.core.LogUtil
import com.fone.filmone.core.login.model.SnsLoginType
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthErrorCode
import com.navercorp.nid.oauth.OAuthLoginCallback

class NaverLoginImpl (
    override val onSuccess: (token: String, snsLoginType: SnsLoginType) -> Unit,
    override val onFail: (message: String) -> Unit,
    override val onCancel: () -> Unit
) : SnsLogin {
    override fun login(context: Context) {
        NaverIdLoginSDK.authenticate(context, NaverOAuthLoginCallback())
    }

    private inner class NaverOAuthLoginCallback : OAuthLoginCallback {
        override fun onError(errorCode: Int, message: String) {
            handleError(errorCode, message)
        }

        override fun onFailure(httpStatus: Int, message: String) {
            onCancel.invoke()
        }

        override fun onSuccess() {
            val accessToken = NaverIdLoginSDK.getAccessToken() ?: run {
                onFail.invoke("No Data")
                return
            }

            LogUtil.d("${SnsLoginType.Naver} $accessToken")
            onSuccess.invoke(accessToken, SnsLoginType.Naver)
        }

        private fun handleError(errorCode: Int, message: String) {
            val code = NaverIdLoginSDK.getLastErrorCode().code

            if (code == NidOAuthErrorCode.CLIENT_USER_CANCEL.code) {
                onCancel.invoke()
            } else {
                if (errorCode == -1) {
                    onCancel.invoke()
                } else {
                    onFail.invoke("errorCode: $errorCode, message: $message")
                }
            }
        }
    }
}