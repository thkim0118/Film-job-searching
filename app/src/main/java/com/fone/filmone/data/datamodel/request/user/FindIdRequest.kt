package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class FindIdRequest(
    val code: Int,
    val phoneNumber: String
)
