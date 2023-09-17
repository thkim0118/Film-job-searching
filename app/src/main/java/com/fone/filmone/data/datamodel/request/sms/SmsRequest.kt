package com.fone.filmone.data.datamodel.request.sms

import com.google.gson.annotations.SerializedName

data class SmsRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("code")
    val code: String
)
