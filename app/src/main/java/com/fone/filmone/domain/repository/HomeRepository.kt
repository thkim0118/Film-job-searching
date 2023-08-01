package com.fone.filmone.domain.repository

import com.fone.filmone.data.datamodel.response.home.HomeItemResponse
import com.fone.filmone.domain.model.common.DataResult

interface HomeRepository {
    suspend fun getHomeItems(): DataResult<HomeItemResponse>
}