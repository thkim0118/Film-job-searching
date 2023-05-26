package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.profiles.ProfilesResponse
import com.fone.filmone.data.datasource.remote.ProfilesApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class ProfilesRepositoryImpl @Inject constructor(
    private val profilesApi: ProfilesApi
) : ProfilesRepository {
    override suspend fun getFavoriteProfiles(
        page: Int,
        size: Int,
        type: Type
    ): DataResult<ProfilesResponse> {
        return handleNetwork { profilesApi.getFavoriteProfile(page, size, type) }
    }

    override suspend fun getMyRegistrations(
        page: Int,
        size: Int,
    ): DataResult<ProfilesResponse> {
        return handleNetwork { profilesApi.getMyRegistrations(page, size) }
    }

    override suspend fun getProfileList(jobTabFilterVo: JobTabFilterVo): DataResult<ProfilesResponse> {
        return handleNetwork {
            profilesApi.getProfileList(
                jobTabFilterVo.ageMax,
                jobTabFilterVo.ageMin,
                jobTabFilterVo.categories.map { it.name },
                jobTabFilterVo.genders.map { it.name },
                jobTabFilterVo.page,
                jobTabFilterVo.size,
                jobTabFilterVo.sort,
                jobTabFilterVo.type
            )
        }
    }
}