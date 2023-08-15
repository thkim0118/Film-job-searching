package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.request.user.RefreshTokenRequest
import com.fone.filmone.data.datamodel.response.user.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("${Server.ApiVersion}/users/reissue")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<NetworkResponse<Token>>
}
