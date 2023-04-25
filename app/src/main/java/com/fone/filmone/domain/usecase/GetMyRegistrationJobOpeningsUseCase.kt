package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetMyRegistrationJobOpeningsUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(): DataResult<MyRegistrationJobOpeningsResponse> {
        return jobOpeningsRepository.getMyRegistrations()
    }
}