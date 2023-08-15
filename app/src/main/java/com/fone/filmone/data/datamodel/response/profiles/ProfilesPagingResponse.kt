package com.fone.filmone.data.datamodel.response.profiles

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.profile.Profiles

@Keep
data class ProfilesPagingResponse(
    val profiles: Profiles
)
