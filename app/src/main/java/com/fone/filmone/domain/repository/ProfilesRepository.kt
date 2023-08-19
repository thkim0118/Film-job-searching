package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.data.datamodel.response.profiles.ProfilesPagingResponse
import com.fone.filmone.data.datamodel.response.profiles.detail.ProfileDetailResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo

interface ProfilesRepository {
    suspend fun wantProfile(
        profileId: Long
    ): DataResult<Unit>

    suspend fun getFavoriteProfiles(
        page: Int,
        size: Int = 20,
        type: Type,
    ): DataResult<ProfilesPagingResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<ProfilesPagingResponse>

    suspend fun getProfileList(jobTabFilterVo: JobTabFilterVo): DataResult<ProfilesPagingResponse>

    suspend fun registerProfile(
        profileRegisterRequest: ProfileRegisterRequest,
    ): DataResult<ProfileDetailResponse>

    suspend fun getProfileDetail(
        profileId: Int,
        type: Type,
    ): DataResult<ProfileDetailResponse>

    suspend fun removeContent(
        profileId: Int,
    ): DataResult<Unit>
}
