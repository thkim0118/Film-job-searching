package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsPagingResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetMyRegistrationJobOpeningsUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository,
) {
    suspend operator fun invoke(
        page: Int,
        size: Int = 20,
    ): DataResult<JobOpeningsPagingResponse> {
        return jobOpeningsRepository.getMyRegistrations(page = page, size = size)
    }
}
