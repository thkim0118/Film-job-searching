package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.response.jobopenings.scrap.MyJobOpeningsScrapResponse
import com.fone.filmone.data.datamodel.response.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class JobOpeningsRepositoryImpl @Inject constructor(
    private val jobOpeningsApi: JobOpeningsApi
) : JobOpeningsRepository {
    override suspend fun getMyJobOpeningsScraps(): DataResult<MyJobOpeningsScrapResponse> {
        return handleNetwork { jobOpeningsApi.getScraps() }
    }

    override suspend fun getMyRegistrations(): DataResult<MyRegistrationJobOpeningsResponse> {
        return handleNetwork { jobOpeningsApi.getMyRegistrations() }
    }
}