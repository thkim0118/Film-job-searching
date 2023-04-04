package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.response.common.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.Server
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("${Server.ApiVersion}/users/check-nickname-duplication")
    suspend fun checkNicknameDuplication(
        @Query("nickname") nickname: String
    ): Response<NetworkResponse<CheckNicknameDuplicationResponse>>
}
