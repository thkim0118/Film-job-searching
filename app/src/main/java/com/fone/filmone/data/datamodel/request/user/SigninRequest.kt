package com.fone.filmone.data.datamodel.request.user

import com.fone.filmone.data.datamodel.response.user.LoginType

data class SigninRequest(
    val accessToken: String,
    val email: String,
    val loginType: LoginType
)
