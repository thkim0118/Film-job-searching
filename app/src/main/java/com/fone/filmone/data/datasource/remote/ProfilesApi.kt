package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.common.paging.SortType
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Gender
import com.fone.filmone.data.datamodel.response.profiles.ProfilesResponse
import retrofit2.Response
import retrofit2.http.GET
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
    ): Response<NetworkResponse<ProfilesResponse>>

    @GET("${Server.ApiVersion}/profiles/wants")
    suspend fun getFavoriteProfile(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<ProfilesResponse>>

    @GET("${Server.ApiVersion}/profiles/my-registrations")
    suspend fun getMyRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<ProfilesResponse>>
}