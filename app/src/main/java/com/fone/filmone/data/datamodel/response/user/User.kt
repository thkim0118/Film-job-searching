package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep

@Keep
data class User(
    val agreeToPersonalInformation: Boolean,
    val agreeToTermsOfServiceTermsOfUse: Boolean,
    val birthday: String,
    val email: String,
    val enabled: Boolean,
    val gender: String,
    val id: Int,
    val interests: List<String>,
    val isReceiveMarketing: Boolean,
    val job: String,
    val nickname: String,
    val phoneNumber: String,
    val profileUrl: String,
    val socialLoginType: String
)