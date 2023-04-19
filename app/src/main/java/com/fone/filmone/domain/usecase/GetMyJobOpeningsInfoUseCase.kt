package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.request.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.jobopenings.JobOpeningsRepository
import javax.inject.Inject

class GetMyJobOpeningsInfoUseCase @Inject constructor(
    private val jobOpeningsRepository: JobOpeningsRepository
) {
    suspend operator fun invoke(): DataResult<MyJobOpeningsResponse> {
        return jobOpeningsRepository.getMyJobOpeningsInfo()
    }
}