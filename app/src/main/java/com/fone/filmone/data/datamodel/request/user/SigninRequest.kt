package com.fone.filmone.data.datamodel.request.user

data class SigninRequest(
    val accessToken: String,
    val email: String,
    val socialLoginType: String
)
