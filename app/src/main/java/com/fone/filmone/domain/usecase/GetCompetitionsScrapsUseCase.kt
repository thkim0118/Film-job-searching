package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.CompetitionsRepository
import javax.inject.Inject

class GetCompetitionsScrapsUseCase @Inject constructor(
    private val competitionsRepository: CompetitionsRepository
) {
    suspend operator fun invoke(page: Int, size: Int = 20): DataResult<CompetitionsResponse> {
        return competitionsRepository.getScraps(page = page, size = size)
    }
}
