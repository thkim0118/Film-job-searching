package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.jobopenings.JobOpeningsRepository
import javax.inject.Inject

class JobOpeningsRepositoryImpl @Inject constructor(
    private val jobOpeningsApi: JobOpeningsApi
) : JobOpeningsRepository {
    override suspend fun getMyJobOpeningsInfo(): DataResult<MyJobOpeningsResponse> {
        return handleNetwork { jobOpeningsApi.getMyJobOpeningInfo() }
    }
}