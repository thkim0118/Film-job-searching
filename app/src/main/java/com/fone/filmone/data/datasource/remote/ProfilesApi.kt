package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfilesApi {

    @GET("${Server.ApiVersion}/profiles/wants")
    suspend fun getFavoriteProfile(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("type") type: Type
    ): Response<NetworkResponse<FavoriteProfilesResponse>>

    @GET("${Server.ApiVersion}/profiles/my-registrations")
    suspend fun getMyRegistrations(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<MyRegistrationsResponse>>
}