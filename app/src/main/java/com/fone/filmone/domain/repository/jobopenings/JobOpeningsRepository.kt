package com.fone.filmone.domain.repository.jobopenings

import com.fone.filmone.data.datamodel.request.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.domain.model.common.DataResult

interface JobOpeningsRepository {
    suspend fun getMyJobOpeningsInfo(): DataResult<MyJobOpeningsResponse>
}