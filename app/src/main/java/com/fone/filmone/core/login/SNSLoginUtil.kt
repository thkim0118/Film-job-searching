package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.core.login.model.SnsLoginType

class SNSLoginUtil(
    private val context: Context,
    private val onSuccess: (token: String, snsLoginType: SnsLoginType) -> Unit,
    private val onFail: (message: String) -> Unit,
    private val onCancel: () -> Unit
) {

    fun login(snsLoginType: SnsLoginType) {
        when (snsLoginType) {
            SnsLoginType.Kakao -> {
                KakaoLoginUtil.getInstance(onSuccess, onFail, onCancel).login(context)
            }
            SnsLoginType.Naver -> TODO()
            SnsLoginType.Google -> TODO()
            SnsLoginType.Apple -> TODO()
        }
    }
}