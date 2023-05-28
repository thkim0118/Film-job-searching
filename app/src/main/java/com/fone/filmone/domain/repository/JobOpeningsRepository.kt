package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsPagingResponse
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.model.jobopenings.JobTabFilterVo

interface JobOpeningsRepository {
    suspend fun getJobOpeningsScraps(
        page: Int,
        size: Int = 20,
        type: Type
    ): DataResult<JobOpeningsPagingResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<JobOpeningsPagingResponse>

    suspend fun getJobOpeningsList(
        jobTabFilterVo: JobTabFilterVo
    ): DataResult<JobOpeningsPagingResponse>

    suspend fun getJobOpeningDetail(
        jobOpeningId: Int,
        type: Type
    ): DataResult<JobOpeningResponse>

    suspend fun registerJobOpening(
        jobOpeningsRegisterRequest: JobOpeningsRegisterRequest
    ): DataResult<JobOpeningResponse>
}