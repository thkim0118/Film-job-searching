package com.fone.filmone.core.login

import android.content.Context

interface SnsLogin {
    val loginCallback: SNSLoginUtil.LoginCallback
    fun login(context: Context)
}
