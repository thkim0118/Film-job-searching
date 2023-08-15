package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.profiles.ProfilesPagingResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class GetProfileListUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(jobTabFilterVo: JobTabFilterVo): DataResult<ProfilesPagingResponse> {
        return profilesRepository.getProfileList(jobTabFilterVo)
    }
}
