package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.profiles.ProfilesPagingResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class GetFavoriteProfilesStaffUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(page: Int, size: Int = 20): DataResult<ProfilesPagingResponse> {
        return profilesRepository.getFavoriteProfiles(
            page = page,
            size = size,
            type = Type.STAFF
        )
    }
}
