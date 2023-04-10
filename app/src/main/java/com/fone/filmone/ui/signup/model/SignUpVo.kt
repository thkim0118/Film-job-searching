package com.fone.filmone.ui.signup.model

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.google.gson.Gson
import java.io.Serializable
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    val profileUrl: String = "",
    val agreeToPersonalInformation: Boolean = false,
    val isReceiveMarketing: Boolean = false,
) : Serializable {
    companion object {
        fun toJson(signUpVo: SignUpVo): String = URLEncoder.encode(
            Gson().toJson(signUpVo),
            StandardCharsets.UTF_8.toString()
        )

        fun fromJson(json: String): SignUpVo = Gson().fromJson(
            URLDecoder.decode(
                json,
                StandardCharsets.UTF_8.toString()
            ),
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
                isReceiveMarketing = isReceiveMarketing
            )
        }
    }
}