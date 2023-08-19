package com.fone.filmone.domain.usecase

import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class RemoveJobOpeningContentUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository,
) {
    suspend operator fun invoke(jobOpeningId: Int): DataResult<Unit> {
        return jobOpeningsRepository.removeContent(jobOpeningId = jobOpeningId)
    }
}
