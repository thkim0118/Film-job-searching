package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep

@Keep
data class SigninResponse(
    val token: Token,
    val user: User
)
