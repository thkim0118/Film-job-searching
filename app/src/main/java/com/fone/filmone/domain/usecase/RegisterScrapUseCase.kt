package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class RegisterScrapUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository,
) {
    suspend operator fun invoke(jobOpeningsId: Int): DataResult<Unit> {
        return jobOpeningsRepository.registerScrap(jobOpeningsId)
    }
}
