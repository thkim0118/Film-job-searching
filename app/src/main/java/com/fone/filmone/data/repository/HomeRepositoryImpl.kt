package com.fone.filmone.data.repository

import com.fone.filmone.data.datamodel.common.network.handleNetwork
import com.fone.filmone.data.datamodel.response.home.HomeItemResponse
import com.fone.filmone.data.datasource.remote.HomeApi
import com.fone.filmone.domain.model.common.DataResult
import com.fone.filmone.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {
    override suspend fun getHomeItems(): DataResult<HomeItemResponse> {
        return handleNetwork { homeApi.getHomeItems() }
    }
}