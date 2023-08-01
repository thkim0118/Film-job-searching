package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class EmailValidationRequest(
    val code: String,
    val email: String
)
