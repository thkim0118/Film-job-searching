package com.fone.filmone.data.datamodel.response.sms

import com.google.gson.annotations.SerializedName

data class SmsTransmitResponse(
    @SerializedName("messageId")
    val messageId: String,
    val debugCode: String?
)
