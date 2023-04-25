package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import com.fone.filmone.data.datamodel.response.profiles.FavoriteProfilesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfilesApi {

    @GET("${Server.ApiVersion}/profiles/wants")
    suspend fun getFavoriteProfile(): Response<NetworkResponse<FavoriteProfilesResponse>>
}