package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.profiles.FavoriteProfilesResponse
import com.fone.filmone.data.datasource.remote.ProfilesApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class ProfilesRepositoryImpl @Inject constructor(
    private val profilesApi: ProfilesApi
) : ProfilesRepository {
    override suspend fun getFavoriteProfiles(): DataResult<FavoriteProfilesResponse> {
        return handleNetwork { profilesApi.getFavoriteProfile() }
    }
}