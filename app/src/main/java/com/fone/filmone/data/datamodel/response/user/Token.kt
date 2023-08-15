package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep

@Keep
data class Token(
    val accessToken: String,
    val expiresIn: Int,
    val issuedAt: String,
    val refreshToken: String,
    val tokenType: String
)
