package com.fone.filmone.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.fone.filmone.core.LogUtil
import com.fone.filmone.core.login.SNSLoginUtil
import com.fone.filmone.core.login.model.SnsLoginType
import com.fone.filmone.ui.navigation.FOneDestinations
import com.fone.filmone.ui.navigation.FOneNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val snsLoginUtil: SNSLoginUtil = SNSLoginUtil.getInstance(
        object : SNSLoginUtil.LoginCallback {
            override fun onSuccess(token: String, snsLoginType: SnsLoginType) {
                LogUtil.d("$snsLoginType, $token")
                FOneNavigator.navigateTo(FOneDestinations.SignUp)
            }

            override fun onFail(message: String) {
                LogUtil.e("Fail :: $message")
            }

            override fun onCancel() {
                LogUtil.w("cancel sns login")
            }

        }
    )

    fun requestSnsAccessToken(
        context: Context,
        snsLoginType: SnsLoginType,
        launcher: ActivityResultLauncher<Intent>? = null
    ) {
        snsLoginUtil.login(context, snsLoginType, launcher)
    }
}