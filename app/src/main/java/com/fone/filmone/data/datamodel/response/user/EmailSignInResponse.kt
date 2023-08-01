package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep

@Keep
data class EmailSignInResponse(
    val token: Token,
    val user: User
)
