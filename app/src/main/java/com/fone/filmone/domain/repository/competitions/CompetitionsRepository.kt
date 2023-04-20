package com.fone.filmone.domain.repository.competitions

import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult

interface CompetitionsRepository {
    suspend fun getCompetitions(): DataResult<CompetitionsResponse>
}