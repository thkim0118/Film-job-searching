package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.response.home.HomeItemResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("${Server.ApiVersion}/homes")
    suspend fun getHomeItems(): Response<NetworkResponse<HomeItemResponse>>
}