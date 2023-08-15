package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CompetitionsApi {
    @GET("${Server.ApiVersion}/competitions/scraps")
    suspend fun getScraps(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<NetworkResponse<CompetitionsResponse>>
}
