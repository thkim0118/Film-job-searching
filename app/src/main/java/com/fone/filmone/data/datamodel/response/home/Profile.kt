package com.fone.filmone.data.datamodel.response.home

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.profile.Profiles
import com.google.gson.annotations.SerializedName

@Keep
data class Profile(
    @SerializedName("data")
    val profile: Profiles,
    val subTitle: String,
    val title: String
)
