package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.domain.model.common.DataResult

interface CompetitionsRepository {
    suspend fun getScraps(page: Int, size: Int = 20): DataResult<CompetitionsResponse>
}
