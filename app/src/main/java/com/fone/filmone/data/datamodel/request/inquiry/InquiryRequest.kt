package com.fone.filmone.data.datamodel.request.inquiry

import androidx.annotation.Keep

@Keep
data class InquiryRequest(
    val agreeToPersonalInformation: Boolean,
    val description: String,
    val email: String,
    val title: String,
    val type: String
)
