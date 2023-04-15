package com.fone.filmone.data.datamodel.response.inquiry

import androidx.annotation.Keep

@Keep
data class Question(
    val agreeToPersonalInformation: Boolean,
    val description: String,
    val email: String,
    val id: Int,
    val title: String,
    val type: String
)