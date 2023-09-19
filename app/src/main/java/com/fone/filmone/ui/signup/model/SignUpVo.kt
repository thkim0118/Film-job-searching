package com.fone.filmone.ui.signup.model

import com.fone.filmone.core.ext.toDecoding
import com.fone.filmone.core.ext.toEncoding
import com.fone.filmone.data.datamodel.request.user.EmailSignUpRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.response.user.LoginType
import com.google.gson.Gson
import java.io.Serializable

data class SignUpVo(
    val name: String = "",
    val accessToken: String = "",
    val loginType: LoginType? = null,
    val email: String = "",
    val password: String = "",
    val token: String = "",
    val job: String = "",
    val interests: List<String> = emptyList(),
    val nickname: String = "",
    val birthday: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val profileUrl: String = "",
    val agreeToPersonalInformation: Boolean = false,
    val agreeToTermsOfServiceTermsOfUse: Boolean = false,
    val isReceiveMarketing: Boolean = false,
    val isNicknameChecked: Boolean = false,
    val isNicknameDuplicated: Boolean = false,
    val isBirthDayChecked: Boolean = false,
    val isProfileUploading: Boolean = false,
    val phoneVerificationState: Boolean = false
) : Serializable {
    companion object {
        fun toJson(signUpVo: SignUpVo): String = Gson().toJson(signUpVo).toEncoding()

        fun fromJson(json: String): SignUpVo = Gson().fromJson(
            json.toDecoding(),
            SignUpVo::class.java
        )

        fun SignUpVo.mapToSignUpRequest(): SignUpRequest {
            return SignUpRequest(
                accessToken = accessToken,
                loginType = loginType!!,
                email = email,
                job = job,
                interests = interests,
                nickname = nickname,
                birthday = birthday,
                gender = gender,
                phoneNumber = phoneNumber,
                profileUrl = profileUrl,
                agreeToPersonalInformation = agreeToPersonalInformation,
                agreeToTermsOfServiceTermsOfUse = agreeToTermsOfServiceTermsOfUse,
                isReceiveMarketing = isReceiveMarketing
            )
        }

        fun SignUpVo.mapToEmailSignUpRequest(): EmailSignUpRequest {
            return EmailSignUpRequest(
                name = name,
                email = email,
                job = job,
                interests = interests,
                nickname = nickname,
                birthday = birthday,
                gender = gender,
                phoneNumber = phoneNumber,
                profileUrl = profileUrl,
                agreeToPersonalInformation = agreeToPersonalInformation,
                agreeToTermsOfServiceTermsOfUse = agreeToTermsOfServiceTermsOfUse,
                isReceiveMarketing = isReceiveMarketing,
                password = password,
                token = token,
            )
        }
    }
}
