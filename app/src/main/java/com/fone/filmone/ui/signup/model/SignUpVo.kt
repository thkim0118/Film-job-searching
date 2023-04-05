package com.fone.filmone.ui.signup.model

import com.google.gson.Gson
import java.io.Serializable

data class SignUpVo(
    val accessToken: String = "",
    val job: String = "",
    val interests: List<String> = emptyList(),
    val nickname: String = "",
    val birthda: String = "",
    val phoneNumber: String = "",
) : Serializable {
    companion object {
        fun toJson(signUpVo: SignUpVo): String = Gson().toJson(signUpVo)
        fun fromJson(json: String): SignUpVo = Gson().fromJson(json, SignUpVo::class.java)
    }
}