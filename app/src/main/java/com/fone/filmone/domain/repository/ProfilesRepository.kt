package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import com.fone.filmone.domain.model.common.DataResult

interface ProfilesRepository {
    suspend fun getFavoriteProfiles(): DataResult<FavoriteProfilesResponse>
    suspend fun getMyRegistrations(): DataResult<MyRegistrationsResponse>
}