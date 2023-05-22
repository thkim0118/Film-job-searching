package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.common.paging.SortType
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings/scraps")
    suspend fun getScraps(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<JobOpeningsResponse>>

    @GET("${Server.ApiVersion}/job-openings/my-registrations")
    suspend fun getMyRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<JobOpeningsResponse>>

    @GET("${Server.ApiVersion}/job-openings/scraps")
    suspend fun getJobOpenings(
        @QueryMap queries: Map<String, Any>,
    ): Response<NetworkResponse<JobOpeningsResponse>>
}