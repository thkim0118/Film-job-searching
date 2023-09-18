package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.user.LoginType

@Keep
data class SigninRequest(
    val accessToken: String,
    val email: String,
    val loginType: LoginType
)
