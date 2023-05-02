package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.response.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.response.common.network.Server
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

    @PATCH("${Server.ApiVersion}/users/sign-out")
    suspend fun signOut(): Response<NetworkResponse<Unit>>

    @GET("${Server.ApiVersion}/users")
    suspend fun getUserInfo(): Response<NetworkResponse<UserResponse>>

    @PATCH("${Server.ApiVersion}/users")
    suspend fun updateUserInfo(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<NetworkResponse<UserResponse>>

    @POST("${Server.ApiVersion}/users/log-out")
    suspend fun logout() :Response<NetworkResponse<Unit>>
}
