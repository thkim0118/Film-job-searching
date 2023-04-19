package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.request.jobopenings.MyJobOpeningsResponse
import com.fone.filmone.data.datamodel.response.common.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.Server
import retrofit2.Response
import retrofit2.http.GET

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings")
    suspend fun getMyJobOpeningInfo(): Response<NetworkResponse<MyJobOpeningsResponse>>
}