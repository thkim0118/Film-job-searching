package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult

interface JobOpeningsRepository {
    suspend fun getMyJobOpeningsInfo(): DataResult<MyJobOpeningsResponse>
}