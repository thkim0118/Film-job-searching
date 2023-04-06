package com.fone.filmone.data.datamodel.request.user

data class SignUpRequest(
    val accessToken: String,
    val agreeToPersonalInformation: Boolean,
    val birthday: String,
    val email: String,
    val gender: String,
    val interests: List<String>,
    val isReceiveMarketing: Boolean,
    val job: String,
    val nickname: String,
    val phoneNumber: String,
    val profileUrl: String,
    val socialLoginType: String
)