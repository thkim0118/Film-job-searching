package com.fone.filmone.data.datamodel.request.sms

import androidx.annotation.Keep

@Keep
data class SmsRequest(
    val phone: String,
    val code: String
)
