package com.fone.filmone.data.datamodel.response.profiles.myregistrations

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.profile.Profiles

@Keep
data class MyRegistrationsResponse(
    val profiles: Profiles
)