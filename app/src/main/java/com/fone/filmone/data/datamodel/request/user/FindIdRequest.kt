package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.user.LoginType

@Keep
data class FindIdRequest(
    val code: Int,
    val phoneNumber: String
)
