package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthErrorCode
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class NaverLoginImpl(
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

            NidOAuthLogin().callProfileApi(
                object : NidProfileCallback<NidProfileResponse> {
                    override fun onError(errorCode: Int, message: String) {
                        handleError(errorCode, message)
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        loginCallback.onCancel()
                    }

                    override fun onSuccess(result: NidProfileResponse) {
                        val email = result.profile?.email ?: run {
                            loginCallback.onFail("email is empty")
                            return
                        }

                        loginCallback.onSuccess(
                            accessToken,
                            email,
                            LoginType.NAVER
                        )
                    }
                }
            )
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