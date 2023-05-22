package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsResponse
import com.fone.filmone.data.datasource.remote.JobOpeningsApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo
import com.fone.filmone.domain.repository.JobOpeningsRepository
import retrofit2.http.Query
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
            jobOpeningsApi.getJobOpenings(
                buildMap {
                    putAll(
                        mapOf(
                            "ageMax" to jobTabFilterVo.ageMax,
                            "ageMin" to jobTabFilterVo.ageMin,
                            "categories" to jobTabFilterVo.categories,
                            "genders" to jobTabFilterVo.genders,
                            "page" to jobTabFilterVo.page,
                            "size" to jobTabFilterVo.size,
                            "sort" to jobTabFilterVo.sort,
                            "type" to jobTabFilterVo.type,
                        )
                    )

                    if (jobTabFilterVo.domains != null) {
                        put("domains", jobTabFilterVo.domains)
                    }
                }
            )
        }
    }
}