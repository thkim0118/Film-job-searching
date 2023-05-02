package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String
)