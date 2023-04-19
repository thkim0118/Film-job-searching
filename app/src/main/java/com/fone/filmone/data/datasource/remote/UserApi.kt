package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.common.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.Server
import com.fone.filmone.data.datamodel.response.user.*
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("${Server.ApiVersion}/users/check-nickname-duplication")
    suspend fun checkNicknameDuplication(
        @Query("nickname") nickname: String
    ): Response<NetworkResponse<CheckNicknameDuplicationResponse>>

    @POST("${Server.ApiVersion}/users/sign-up")
    suspend fun signUp(
        @Body signupRequest: SignUpRequest
    ): Response<NetworkResponse<SignUpResponse>>

    @POST("${Server.ApiVersion}/users/sign-in")
    suspend fun signIn(
        @Body signInRequest: SigninRequest
    ): Response<NetworkResponse<SigninResponse>>

    @GET("${Server.ApiVersion}/users")
    suspend fun getUserInfo(): Response<NetworkResponse<UserResponse>>

    @PATCH("${Server.ApiVersion}/users")
    suspend fun updateUserInfo(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<NetworkResponse<UserResponse>>
}
