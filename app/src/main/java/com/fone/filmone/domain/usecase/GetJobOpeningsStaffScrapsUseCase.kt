package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetJobOpeningsStaffScrapsUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(page: Int, size: Int = 20): DataResult<JobOpeningsResponse> {
        return jobOpeningsRepository.getJobOpeningsScraps(
            page = page,
            size = size,
            type = Type.STAFF
        )
    }
}