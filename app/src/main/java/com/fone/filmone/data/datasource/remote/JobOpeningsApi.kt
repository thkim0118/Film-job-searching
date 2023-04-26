package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.jobopenings.Type
import com.fone.filmone.data.datamodel.response.jobopenings.scrap.JobOpeningsScrapResponse
import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import com.fone.filmone.data.datamodel.response.jobopenings.myregistration.MyRegistrationJobOpeningsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings/scraps")
    suspend fun getScraps(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<JobOpeningsScrapResponse>>

    @GET("${Server.ApiVersion}/job-openings/my-registrations")
    suspend fun getMyRegistrations(): Response<NetworkResponse<MyRegistrationJobOpeningsResponse>>
}