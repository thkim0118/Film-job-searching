package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.ProfilesRepository
import javax.inject.Inject

class GetMyRegistrationProfileUseCase @Inject constructor(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(): DataResult<MyRegistrationsResponse> {
        return profilesRepository.getMyRegistrations()
    }
}