package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import com.fone.filmone.domain.model.common.DataResult

interface ProfilesRepository {
    suspend fun getFavoriteProfiles(
        page: Int,
        size: Int = 20,
        type: Type
    ): DataResult<FavoriteProfilesResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<MyRegistrationsResponse>
}