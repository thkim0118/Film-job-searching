package com.fone.filmone.data.datamodel.response.sms

import com.google.gson.annotations.SerializedName

data class SmsVerificationResponse(
    @SerializedName("messageId")
    val messageId: String
)
