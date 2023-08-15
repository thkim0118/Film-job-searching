package com.fone.filmone.data.datasource.remote

import com.fone.filmone.data.datamodel.common.network.NetworkResponse
import com.fone.filmone.data.datamodel.common.network.Server
import com.fone.filmone.data.datamodel.request.user.ChangePasswordRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignInRequest
import com.fone.filmone.data.datamodel.request.user.EmailSignUpRequest
import com.fone.filmone.data.datamodel.request.user.EmailValidationRequest
import com.fone.filmone.data.datamodel.request.user.FindIdRequest
import com.fone.filmone.data.datamodel.request.user.FindPasswordRequest
import com.fone.filmone.data.datamodel.request.user.SignUpRequest
import com.fone.filmone.data.datamodel.request.user.SigninRequest
import com.fone.filmone.data.datamodel.request.user.UserUpdateRequest
import com.fone.filmone.data.datamodel.request.user.ValidatePasswordRequest
import com.fone.filmone.data.datamodel.response.user.CheckNicknameDuplicationResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignInResponse
import com.fone.filmone.data.datamodel.response.user.EmailSignUpResponse
import com.fone.filmone.data.datamodel.response.user.EmailValidationResponse
import com.fone.filmone.data.datamodel.response.user.FindIdResponse
import com.fone.filmone.data.datamodel.response.user.FindPasswordResponse
import com.fone.filmone.data.datamodel.response.user.SignUpResponse
import com.fone.filmone.data.datamodel.response.user.SigninResponse
import com.fone.filmone.data.datamodel.response.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("${Server.ApiVersion}/users/check-nickname-duplication")
    suspend fun checkNicknameDuplication(
        @Query("nickname") nickname: String
    ): Response<NetworkResponse<CheckNicknameDuplicationResponse>>

    @POST("${Server.ApiVersion}/users/social/sign-up")
    suspend fun signUp(
        @Body signupRequest: SignUpRequest
    ): Response<NetworkResponse<SignUpResponse>>

    @POST("${Server.ApiVersion}/users/social/sign-in")
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
    suspend fun logout(): Response<NetworkResponse<Unit>>

    @POST("${Server.ApiVersion}/users/email/sign-in")
    suspend fun emailSignIn(@Body emailSignInRequest: EmailSignInRequest): Response<NetworkResponse<EmailSignInResponse>>

    @POST("${Server.ApiVersion}/users/email/sign-up")
    suspend fun emailSignUp(@Body emailSignUpRequest: EmailSignUpRequest): Response<NetworkResponse<EmailSignUpResponse>>

    @PATCH("${Server.ApiVersion}/users/password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<NetworkResponse<Unit>>

    @POST("${Server.ApiVersion}/users/password/validate")
    suspend fun validatePassword(@Body validatePasswordRequest: ValidatePasswordRequest): Response<NetworkResponse<Unit>>

    @POST("${Server.ApiVersion}/users/sms/find-id")
    suspend fun findId(@Body findIdRequest: FindIdRequest): Response<NetworkResponse<FindIdResponse>>

    @POST("${Server.ApiVersion}/users/sms/find-password")
    suspend fun findPassword(@Body findPasswordRequest: FindPasswordRequest): Response<NetworkResponse<FindPasswordResponse>>

    @POST("${Server.ApiVersion}/users/email/validate")
    suspend fun validateEmail(@Body emailValidationRequest: EmailValidationRequest): Response<NetworkResponse<EmailValidationResponse>>
}
