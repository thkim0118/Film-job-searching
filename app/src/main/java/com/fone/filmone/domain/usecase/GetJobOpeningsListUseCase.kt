package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsPagingResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetJobOpeningsListUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(jobTabFilterVo: JobTabFilterVo): DataResult<JobOpeningsPagingResponse> {
        return jobOpeningsRepository.getJobOpeningsList(jobTabFilterVo)
    }
}
