package com.fone.filmone.data.datamodel.request.user

import androidx.annotation.Keep

@Keep
data class EmailSignUpRequest(
    val agreeToPersonalInformation: Boolean,
    val agreeToTermsOfServiceTermsOfUse: Boolean,
    val birthday: String,
    val email: String,
    val gender: String,
    val interests: List<String>,
    val isReceiveMarketing: Boolean,
    val job: String,
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val profileUrl: String,
    val token: String
)
