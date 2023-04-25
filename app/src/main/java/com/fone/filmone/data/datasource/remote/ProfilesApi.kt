package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import com.fone.filmone.data.datamodel.response.profiles.favorite.FavoriteProfilesResponse
import com.fone.filmone.data.datamodel.response.profiles.myregistrations.MyRegistrationsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfilesApi {

    @GET("${Server.ApiVersion}/profiles/wants")
    suspend fun getFavoriteProfile(): Response<NetworkResponse<FavoriteProfilesResponse>>

    @GET("${Server.ApiVersion}/profiles/my-registrations")
    suspend fun getMyRegistrations(): Response<NetworkResponse<MyRegistrationsResponse>>
}