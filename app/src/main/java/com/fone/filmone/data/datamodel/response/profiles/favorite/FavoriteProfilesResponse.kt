package com.fone.filmone.data.datamodel.response.profiles.favorite

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.response.common.profile.Profiles

@Keep
data class FavoriteProfilesResponse(
    val profiles: Profiles
)