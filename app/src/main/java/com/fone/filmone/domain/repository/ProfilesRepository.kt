package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.profiles.ProfilesResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo

interface ProfilesRepository {
    suspend fun getFavoriteProfiles(
        page: Int,
        size: Int = 20,
        type: Type
    ): DataResult<ProfilesResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<ProfilesResponse>

    suspend fun getProfileList(jobTabFilterVo: JobTabFilterVo): DataResult<ProfilesResponse>
}