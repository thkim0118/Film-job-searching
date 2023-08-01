package com.fone.filmone.ui.profile.list.model

import com.fone.filmone.core.ext.toDecoding
import com.fone.filmone.core.ext.toEncoding
import com.google.gson.Gson

data class ProfileListArguments(
    val userName: String,
    val profileUrls: List<String>
) {
    companion object {
        fun toJson(profileListArguments: ProfileListArguments): String =
            Gson().toJson(profileListArguments).toEncoding()

        fun fromJson(json: String): ProfileListArguments = Gson().fromJson(
            json.toDecoding(),
            ProfileListArguments::class.java
        )
    }
}
