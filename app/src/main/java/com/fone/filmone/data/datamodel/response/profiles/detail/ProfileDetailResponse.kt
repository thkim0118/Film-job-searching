package com.fone.filmone.data.datamodel.response.profiles.detail

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.profile.ProfileDetailContent

@Keep
data class ProfileDetailResponse(
    val profile: ProfileDetailContent
)