package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class CheckEmailDuplicationRequest(
    val email: String
)
