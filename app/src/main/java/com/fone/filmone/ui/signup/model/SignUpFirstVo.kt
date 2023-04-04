package com.fone.filmone.ui.signup.model

import com.google.gson.Gson
import java.io.Serializable

data class SignUpFirstVo(
    val job: String,
    val interests: List<String>
) : Serializable {
    companion object {
        fun toJson(signUpFirstVo: SignUpFirstVo): String = Gson().toJson(signUpFirstVo)
        fun fromJson(json: String): SignUpFirstVo = Gson().fromJson(json, SignUpFirstVo::class.java)
    }
}
