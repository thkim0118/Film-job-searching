package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetJobOpeningDetailUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(
        jobOpeningId: Int,
        type: Type
    ): DataResult<JobOpeningResponse> {
        return jobOpeningsRepository.getJobOpeningDetail(
            jobOpeningId = jobOpeningId,
            type = type
        )
    }
}
