package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import com.fone.filmone.domain.model.common.DataResult

interface ProfilesRepository {
    suspend fun getFavoriteProfiles(
        page: Int,
        size: Int,
        type: Type
    ): DataResult<FavoriteProfilesResponse>

    suspend fun getMyRegistrations(): DataResult<MyRegistrationsResponse>
}