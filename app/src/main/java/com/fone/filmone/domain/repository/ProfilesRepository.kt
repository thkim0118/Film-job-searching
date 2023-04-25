package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.profiles.FavoriteProfilesResponse
import com.fone.filmone.domain.model.common.DataResult

interface ProfilesRepository {
    suspend fun getFavoriteProfiles(): DataResult<FavoriteProfilesResponse>
}