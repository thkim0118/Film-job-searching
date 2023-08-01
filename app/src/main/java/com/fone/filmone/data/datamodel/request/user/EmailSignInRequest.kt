package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class EmailSignInRequest(
    val email: String,
    val password: String
)
