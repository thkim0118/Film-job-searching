package com.fone.filmone.data.datamodel.response.user

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender

@Keep
data class User(
    val agreeToPersonalInformation: Boolean,
    val agreeToTermsOfServiceTermsOfUse: Boolean,
    val birthday: String,
    val email: String,
    val enabled: Boolean,
    val gender: Gender,
    val id: Int,
    val interests: List<Category>,
    val isReceiveMarketing: Boolean,
    val job: Job,
    val nickname: String,
    val phoneNumber: String,
    val profileUrl: String,
    val socialLoginType: SocialLoginType
)