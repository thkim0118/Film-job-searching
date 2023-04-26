package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.scrap.JobOpeningsScrapResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetJobOpeningsActorScrapsUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(page: Int, size: Int = 20): DataResult<JobOpeningsScrapResponse> {
        return jobOpeningsRepository.getJobOpeningsScraps(
            page = page,
            size = size,
            type = Type.ACTOR
        )
    }
}