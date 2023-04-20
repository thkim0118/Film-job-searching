package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
import com.fone.filmone.data.datamodel.response.competition.CompetitionsResponse
import retrofit2.Response
import retrofit2.http.GET

interface CompetitionsApi {
    @GET("${Server.ApiVersion}/competitions")
    suspend fun getCompetitions(): Response<NetworkResponse<CompetitionsResponse>>
}