package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.jobopenings.scrap.JobOpeningsScrapResponse
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class JobOpeningsRepositoryImpl @Inject constructor(
    private val jobOpeningsApi: JobOpeningsApi
) : JobOpeningsRepository {
    override suspend fun getJobOpeningsScraps(
        page: Int,
        size: Int,
        type: Type
    ): DataResult<JobOpeningsScrapResponse> {
        return handleNetwork { jobOpeningsApi.getScraps(page = page, size = size, type = type) }
    }

    override suspend fun getMyRegistrations(
        page: Int,
        size: Int,
    ): DataResult<MyRegistrationJobOpeningsResponse> {
        return handleNetwork { jobOpeningsApi.getMyRegistrations(page = page, size = size) }
    }
}