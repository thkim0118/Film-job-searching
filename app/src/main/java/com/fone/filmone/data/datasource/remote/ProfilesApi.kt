package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.common.paging.SortType
import com.fone.filmone.data.datamodel.request.profile.ProfileRegisterRequest
import com.fone.filmone.data.datamodel.response.profiles.ProfilesPagingResponse
import com.fone.filmone.data.datamodel.response.profiles.detail.ProfileDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfilesApi {
    @GET("${Server.ApiVersion}/profiles")
    suspend fun getProfileList(
        @Query("ageMax") ageMax: Int,
        @Query("ageMin") ageMin: Int,
        @Query("categories") categories: List<String>,
        @Query("genders") genders: List<String>,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: SortType,
        @Query("type") type: Type,
    ): Response<NetworkResponse<ProfilesPagingResponse>>

    @GET("${Server.ApiVersion}/profiles/wants")
    suspend fun getFavoriteProfile(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<ProfilesPagingResponse>>

    @GET("${Server.ApiVersion}/profiles/my-registrations")
    suspend fun getMyRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<ProfilesPagingResponse>>

    @POST("${Server.ApiVersion}/profiles")
    suspend fun registerProfile(
        @Body profileRegisterRequest: ProfileRegisterRequest
    ): Response<NetworkResponse<ProfileDetailResponse>>

    @GET("${Server.ApiVersion}/profiles/{profileId}")
    suspend fun getProfileDetail(
        @Path("profileId") profileId: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<ProfileDetailResponse>>
}