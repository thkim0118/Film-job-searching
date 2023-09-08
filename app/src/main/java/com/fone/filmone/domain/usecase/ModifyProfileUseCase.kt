package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.data.datamodel.response.profiles.detail.ProfileDetailResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class ModifyProfileUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository,
) {
    suspend operator fun invoke(
        profileId: Int,
        profileRegisterRequest: ProfileRegisterRequest,
    ): DataResult<ProfileDetailResponse> {
        return profilesRepository.modifyContent(profileId, profileRegisterRequest)
    }
}
