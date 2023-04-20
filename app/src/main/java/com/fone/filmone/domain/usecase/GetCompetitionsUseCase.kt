package com.fone.filmone.domain.usecase

import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.competitions.CompetitionsRepository
import javax.inject.Inject

class GetCompetitionsUseCase @Inject constructor(
    private val competitionsRepository: CompetitionsRepository
) {
    suspend operator fun invoke(): DataResult<CompetitionsResponse> {
        return competitionsRepository.getCompetitions()
    }
}