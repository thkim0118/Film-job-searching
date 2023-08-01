package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.network.ResponseType

@Keep
data class EmailValidationResponse(
    val responseType: ResponseType,
    val token: String
)
