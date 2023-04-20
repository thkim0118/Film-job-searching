package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import retrofit2.Response
import retrofit2.http.GET

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings")
    suspend fun getMyJobOpeningInfo(): Response<NetworkResponse<MyJobOpeningsResponse>>
}