package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import com.fone.filmone.data.datasource.remote.CompetitionsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.CompetitionsRepository
import javax.inject.Inject

class CompetitionsRepositoryImpl @Inject constructor(
    private val competitionsApi: CompetitionsApi
) : CompetitionsRepository {
    override suspend fun getScraps(
        page: Int,
        size: Int
    ): DataResult<CompetitionsResponse> {
        return handleNetwork { competitionsApi.getScraps(page, size) }
    }
}
