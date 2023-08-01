package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class FindPasswordRequest(
    val code: Int,
    val phoneNumber: String
)
