package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.common.paging.SortType
import com.fone.filmone.data.datamodel.request.jobopening.JobOpeningsRegisterRequest
import com.fone.filmone.data.datamodel.response.jobopenings.JobOpeningsPagingResponse
import com.fone.filmone.data.datamodel.response.jobopenings.detail.JobOpeningResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JobOpeningsApi {
    @GET("${Server.ApiVersion}/job-openings")
    suspend fun fetchJobOpeningList(
        @Query("ageMax") ageMax: Int,
        @Query("ageMin") ageMin: Int,
        @Query("categories") categories: List<String>,
        @Query("domains") domains: List<String>?,
        @Query("genders") genders: List<String>,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: SortType,
        @Query("type") type: Type,
    ): Response<NetworkResponse<JobOpeningsPagingResponse>>

    @GET("${Server.ApiVersion}/job-openings/scraps")
    suspend fun getScraps(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type,
    ): Response<NetworkResponse<JobOpeningsPagingResponse>>

    @GET("${Server.ApiVersion}/job-openings/my-registrations")
    suspend fun getMyRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<JobOpeningsPagingResponse>>

    @GET("${Server.ApiVersion}/job-openings/{jobOpeningId}")
    suspend fun getJobOpeningDetail(
        @Path("jobOpeningId") jobOpeningId: Int,
        @Query("type") type: Type,
    ): Response<NetworkResponse<JobOpeningResponse>>

    @POST("${Server.ApiVersion}/job-openings")
    suspend fun registerJobOpening(
        @Body jobOpeningsRegisterRequest: JobOpeningsRegisterRequest,
    ): Response<NetworkResponse<JobOpeningResponse>>

    @DELETE("${Server.ApiVersion}/job-openings/{jobOpeningId}")
    suspend fun removeContent(
        @Path("jobOpeningId") jobOpeningId: Int,
    ): Response<NetworkResponse<Unit>>

    @PUT("${Server.ApiVersion}/job-openings/{jobOpeningId}")
    suspend fun modifyContent(
        @Path("jobOpeningId") jobOpeningId: Int,
        @Body jobOpeningsRegisterRequest: JobOpeningsRegisterRequest,
    ): Response<NetworkResponse<JobOpeningResponse>>

    @POST("${Server.ApiVersion}/job-openings/{jobOpeningsId}/scrap")
    suspend fun registerScrap(
        @Path("jobOpeningsId") jobOpeningsId: Int,
    ): Response<NetworkResponse<Unit>>
}
