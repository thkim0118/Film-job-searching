package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsResponse
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.repository.JobOpeningsRepository
import javax.inject.Inject

class JobOpeningsRepositoryImpl @Inject constructor(
    private val jobOpeningsApi: JobOpeningsApi
) : JobOpeningsRepository {
    override suspend fun getJobOpeningsScraps(
        page: Int,
        size: Int,
        type: Type
    ): DataResult<JobOpeningsResponse> {
        return handleNetwork { jobOpeningsApi.getScraps(page = page, size = size, type = type) }
    }

    override suspend fun getMyRegistrations(
        page: Int,
        size: Int,
    ): DataResult<JobOpeningsResponse> {
        return handleNetwork { jobOpeningsApi.getMyRegistrations(page = page, size = size) }
    }

    override suspend fun getJobOpeningsList(jobTabFilterVo: JobTabFilterVo): DataResult<JobOpeningsResponse> {
        return handleNetwork {
            jobOpeningsApi.fetchJobOpeningList(
                jobTabFilterVo.ageMax,
                jobTabFilterVo.ageMin,
                jobTabFilterVo.categories.map { it.name },
                jobTabFilterVo.domains?.map { it.name },
                jobTabFilterVo.genders.map { it.name },
                jobTabFilterVo.page,
                jobTabFilterVo.size,
                jobTabFilterVo.sort,
                jobTabFilterVo.type,
            )
        }
    }
}