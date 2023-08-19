package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class WantProfileUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {

    suspend operator fun invoke(profileId: Long): DataResult<Unit> {
        return profilesRepository.wantProfile(profileId = profileId)
    }
}
