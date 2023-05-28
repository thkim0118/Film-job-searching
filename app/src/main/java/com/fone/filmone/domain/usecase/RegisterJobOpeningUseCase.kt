package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class RegisterJobOpeningUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(jobOpeningsRegisterRequest: JobOpeningsRegisterRequest): DataResult<JobOpeningResponse> {
        return jobOpeningsRepository.registerJobOpening(jobOpeningsRegisterRequest)
    }
}