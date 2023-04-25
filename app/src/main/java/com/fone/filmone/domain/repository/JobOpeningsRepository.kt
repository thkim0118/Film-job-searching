package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.jobopenings.scrap.MyJobOpeningsScrapResponse
import com.fone.filmone.domain.model.common.DataResult

interface JobOpeningsRepository {
    suspend fun getMyJobOpeningsScraps(): DataResult<MyJobOpeningsScrapResponse>
    suspend fun getMyRegistrations(): DataResult<MyRegistrationJobOpeningsResponse>
}