package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.jobopenings.scrap.MyJobOpeningsScrapResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class GetMyJobOpeningsScrapsUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(): DataResult<MyJobOpeningsScrapResponse> {
        return jobOpeningsRepository.getMyJobOpeningsScraps()
    }
}