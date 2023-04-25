package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.jobopenings.scrap.MyJobOpeningsScrapResponse
import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import retrofit2.Response
import retrofit2.http.GET

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings")
    suspend fun getScraps(): Response<NetworkResponse<MyJobOpeningsScrapResponse>>

    @GET("${Server.ApiVersion}/job-openings/my-registrations")
    suspend fun getMyRegistrations(): Response<NetworkResponse<MyRegistrationJobOpeningsResponse>>
}