package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.jobopenings.scrap.JobOpeningsScrapResponse
import com.fone.filmone.domain.model.common.DataResult

interface JobOpeningsRepository {
    suspend fun getJobOpeningsScraps(
        page: Int,
        size: Int = 20,
        type: Type
    ): DataResult<JobOpeningsScrapResponse>

    suspend fun getMyRegistrations(
        page: Int,
        size: Int = 20,
    ): DataResult<MyRegistrationJobOpeningsResponse>
}