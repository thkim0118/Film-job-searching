package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class ChangePasswordRequest(
    val password: String,
    val phoneNumber: String,
    val token: String
)
