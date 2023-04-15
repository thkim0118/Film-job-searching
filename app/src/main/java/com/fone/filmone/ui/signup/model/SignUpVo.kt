package com.fone.filmone.ui.signup.model

import com.fone.filmone.core.ext.toDecoding
import com.fone.filmone.core.ext.toEncoding
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.google.gson.Gson
import java.io.Serializable

data class SignUpVo(
    val accessToken: String = "",
    val socialLoginType: String = "",
    val email: String = "",
    val job: String = "",
    val interests: List<String> = emptyList(),
    val nickname: String = "",
    val birthday: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val encodingImage: String = "",
    val profileUrl: String = "",
    val agreeToPersonalInformation: Boolean = false,
    val agreeToTermsOfServiceTermsOfUse: Boolean = false,
    val isReceiveMarketing: Boolean = false,
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
                socialLoginType = socialLoginType,
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
    }
}