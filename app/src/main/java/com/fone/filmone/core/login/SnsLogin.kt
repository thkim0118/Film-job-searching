package com.fone.filmone.core.login

import android.content.Context
import com.fone.filmone.core.login.model.SnsLoginType

interface SnsLogin {
    val onSuccess: (token: String, snsLoginType: SnsLoginType) -> Unit
    val onFail: (message: String) -> Unit
    val onCancel: () -> Unit

    fun login(context: Context)
}