package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep

@Keep
data class FindIdResponse(
    val loginType: LoginType,
    val email: String
)
