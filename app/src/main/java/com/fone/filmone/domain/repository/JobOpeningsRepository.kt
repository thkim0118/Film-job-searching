package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo

interface JobOpeningsRepository {
    suspend fun getJobOpeningsScraps(
        page: Int,
        size: Int = 20,
        type: Type
    ): DataResult<JobOpeningsResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<JobOpeningsResponse>

    suspend fun getJobOpeningsList(
        jobTabFilterVo: JobTabFilterVo
    ): DataResult<JobOpeningsResponse>
}